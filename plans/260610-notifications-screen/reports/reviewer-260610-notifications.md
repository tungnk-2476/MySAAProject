# Code Review — Notifications Feature
Date: 2026-06-10
Reviewer: reviewer agent (Staff Engineer)

---

## Scope
- Files: 11 (2 data, 5 ui/notifications, 3 modified, 1 test)
- LOC: ~450 new / ~60 modified
- Focus: correctness of shared-singleton state, concurrency, Compose patterns, auth lifecycle

---

## Overall Assessment

Implementation is clean, deliberately simple, and matches the plan. The shared-singleton
pattern is understood and intentional. The core correctness story is solid. There are no
data-loss or injection bugs. Issues found are mostly a **Major** lifecycle gap (singleton
survives logout), one **Minor** UI/navigation concern, and several **Nit**-level items.

---

## Critical Issues

None.

---

## High Priority

### H1 — Singleton state not cleared on logout (`NotificationsRepository`, `AppNavHost.kt:52-55`)

The `object NotificationsRepository` holds its `MutableStateFlow` for the lifetime of the
process. When the user logs out (`clearSession()`), the notification state (read/unread markers)
from the previous session survives. If a second user logs in on the same device, or the same
user logs out and back in, they see the stale read-state from the last session.

This is not an issue today because the seed is static mock data and there is only one user.
But the `reset()` function already exists — it just is never called at logout.

**Fix:** Call `NotificationsRepository.reset()` inside the `onLogout` lambda in `AppRoot.kt`
alongside `sessionRepository.clearSession()`.

```kotlin
// AppRoot.kt
onLogout = {
    scope.launch {
        sessionRepository.clearSession()
        NotificationsRepository.reset()   // clear read-state for next session
    }
},
```

This is low-risk and makes the eventual real-data migration safer.

---

## Medium Priority

### M1 — `navController.navigate(Routes.NOTIFICATIONS)` without `launchSingleTop` (`AppNavHost.kt:58`)

Rapid double-taps on the bell (or a debounce gap) can push two `NOTIFICATIONS` entries onto
the back stack. Back-pressing then requires two presses to return to Home.

**Fix:**
```kotlin
onOpenNotifications = {
    navController.navigate(Routes.NOTIFICATIONS) {
        launchSingleTop = true
    }
},
```

This is consistent with how standard Android navigation handles non-root destinations.

### M2 — `NotificationsScreen` preview imports `NotificationsRepository` directly (`NotificationsScreen.kt:33,146`)

The preview at line 141–150 reads `NotificationsRepository.items.value` directly from the
singleton rather than using hardcoded/preview-local data. This couples the preview to
production state at preview-render time and is a smell: if `seed()` ever throws (e.g., after
a DB migration), the preview breaks silently.

The data layer import (`import ...NotificationsRepository`) does not belong in a presentational
screen file. The screen is otherwise fully state-hoisted (no other direct singleton references).

**Fix:** Replace the preview data with an inline list literal or a `@PreviewParameter`.
```kotlin
private val previewItems = listOf(
    NotificationItem("p1", NotificationType.KUDOS_RECEIVED, "Preview message", "5m", false),
    NotificationItem("p2", NotificationType.HEART_RECEIVED, "Another message", "1h", true),
)
// ...
items = previewItems,
```
Remove the `NotificationsRepository` import from `NotificationsScreen.kt`.

---

## Minor Issues

### N1 — Back icon click target is 24 dp — below 48 dp minimum (`NotificationsScreen.kt:100-104`)

```kotlin
modifier = Modifier
    .align(Alignment.CenterStart)
    .size(24.dp)
    .clickable(onClick = onBack),   // 24×24 tap target
```

The icon size is 24 dp but the tap target is the same 24 dp box. Android accessibility
guidelines require a minimum of 48×48 dp. The `TopBar` Box is 44 dp tall, so the target is
also height-clipped.

**Fix:** Wrap in a `Box` with `48.dp` size and `Modifier.minimumInteractiveComponentSize()`,
or use `IconButton` (which provides 48 dp out of the box):
```kotlin
IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
    Icon(painter = painterResource(R.drawable.ic_arrow_back), ...)
}
```
The same issue exists for the bell icon click target in `HomeHeader.kt:77-78`
(pre-existing, not introduced here, but worth noting).

### N2 — `SaaBadge` and `SaaUnreadDot` are two different reds for the same semantic concept

`SaaBadge` = `#FF3B30` (Home bell badge dot)
`SaaUnreadDot` = `#D4271D` (Notification row unread dot)

If both represent "unread indicator" these should be the same design token unless the spec
explicitly distinguishes them. Verify against MoMorph. If they should match, unify to one
constant (or rename clearly: `SaaHomeBadge` vs `SaaRowUnreadDot`).

### N3 — `markRead` called even for already-read items (`NotificationsRoute.kt:23-24`)

```kotlin
onItemClick = { item ->
    viewModel.markRead(item.id)  // called regardless of item.isRead
```

`markRead` on an already-read item triggers a `_items.update {}` that maps the entire list
and emits a new StateFlow value (even though each element's `isRead` stays `true`). This
causes an unnecessary recomposition of the LazyColumn.

**Fix:** Guard the call:
```kotlin
onItemClick = { item ->
    if (!item.isRead) viewModel.markRead(item.id)
```

Or, preferably, move the guard into `NotificationsRepository.markRead()`:
```kotlin
fun markRead(id: String) {
    if (_items.value.none { it.id == id && !it.isRead }) return
    _items.update { list -> list.map { if (it.id == id) it.copy(isRead = true) else it } }
}
```

---

## Nit-level

### Nit1 — `ic_flag_jp.xml` exists in `drawable/` but `AppLanguage` has no JP entry

`ic_flag_jp.xml` is present in the drawable folder but is unreferenced. This is dead
resource. Either remove it, or note it as a placeholder for a future JP locale. It will not
cause a compile error but adds confusion.

### Nit2 — `hasCommunityStandardsLink` is a property on `NotificationType` inside a UI file

`val NotificationType.hasCommunityStandardsLink` is defined in `NotificationTypeUi.kt` (UI
layer). Since it references only `NotificationType` (a data enum) and expresses a
domain concept ("does this type carry a community-standards link?"), it arguably belongs in
`NotificationItem.kt` or a domain extension file. However, given the intentional data/ui
boundary choice here (data layer is pure), keeping it in the UI layer is acceptable — just
note the conceptual mismatch.

### Nit3 — Test isolation relies on `@Before reset()` but `reset()` is public API

`reset()` on the singleton is `public` — any production code can accidentally call it.
Consider scoping it to `internal` (and marking with `@VisibleForTesting`) so it cannot be
called from feature code:
```kotlin
@VisibleForTesting
internal fun reset() { _items.value = seed() }
```

### Nit4 — `HomeViewModel` uses `SharingStarted.WhileSubscribed(5_000)` for `unreadCount`

With `WhileSubscribed(5_000)`, if the user navigates away from Home for more than 5 seconds
and returns, the upstream `NotificationsRepository.items` flow will be restarted and a new
`stateIn` subscription opened. Because the repository is a cold `StateFlow`, the new
subscriber gets the current value immediately — so there is no functional bug. Documenting
this as expected behavior is worth a comment in `HomeViewModel` to prevent future devs from
bumping the timeout.

---

## Edge Cases Found

1. **Process death / restore:** Since state is in-memory only, a process kill clears all
   read-state. On restore the badge will show 1 (or whatever seed says) even if the user
   previously read everything. This is a known limitation of the mock/in-memory design.
   No fix needed now, but it should be called out when real persistence is added.

2. **Concurrent `markRead` + `markAllRead`:** `MutableStateFlow.update {}` is atomic (uses
   `compareAndSet` loop internally in coroutines library). There is no lost-update race
   between two simultaneous `update` calls. This is safe.

3. **Empty list state:** If `seed()` is replaced with an empty list (or future API returns
   empty), `LazyColumn` will render nothing — no empty-state placeholder is shown. The
   design spec was for 7 items so this is fine for now, but flag for the real-data phase.

---

## Positive Observations

- **`MutableStateFlow.update {}`** used correctly throughout — no direct `.value =` mutation
  that would lose atomicity.
- **`collectAsStateWithLifecycle`** used consistently across both Route files — correct
  lifecycle-aware collection.
- **`stateIn(WhileSubscribed(5_000))`** on `unreadCount` in `HomeViewModel` is idiomatic and
  avoids leaking the coroutine when Home is off-screen.
- `LazyColumn` items have `key = { it.id }` — correct, prevents full recomposition on
  markRead-driven list update.
- `NotificationItem` is an immutable `data class`; `copy()` used for all mutations —
  structurally sound for StateFlow.
- Presentational screens are fully state-hoisted (no ViewModel references inside Screen/Row
  composables) — consistent with Login/Home patterns.
- `reset()` function exists and is used in tests — good foresight.
- Strings are fully localized (VI + EN); no hardcoded display strings in composables.

---

## Recommended Actions (Prioritized)

1. **(H1)** Call `NotificationsRepository.reset()` on logout in `AppRoot.kt`.
2. **(M1)** Add `launchSingleTop = true` to the NOTIFICATIONS navigation call.
3. **(M2)** Remove `NotificationsRepository` import from `NotificationsScreen.kt`; use
   inline data in the preview.
4. **(N1)** Fix back-arrow tap target to 48 dp (use `IconButton`).
5. **(N3)** Guard `markRead` for already-read items (avoid spurious StateFlow emissions).
6. **(Nit1)** Remove unused `ic_flag_jp.xml` or document as placeholder.
7. **(Nit3)** Mark `reset()` as `@VisibleForTesting internal`.

---

## Metrics

- Type Coverage: ~100% (no `Any` usage, no unsafe casts observed)
- Test Coverage: repository logic covered (4 tests); no ViewModel or Compose UI tests
- Linting Issues: 0 compiler errors; minor accessibility gap (N1)

---

## Unresolved Questions

- Are `SaaBadge` (#FF3B30) and `SaaUnreadDot` (#D4271D) intentionally different per design
  spec, or should they be unified? (Nit2)
- Will `ic_flag_jp.xml` be used when a JP locale is added, or is it a leftover? (Nit1)
