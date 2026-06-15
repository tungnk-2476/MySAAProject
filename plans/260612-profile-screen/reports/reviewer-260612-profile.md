# Code Review — Profile Screen Feature
**Date:** 2026-06-12  
**Reviewer:** reviewer agent  
**Status:** DONE_WITH_CONCERNS

---

## Scope

| | |
|---|---|
| New files | 11 (Profile.kt, ProfileRepository.kt, ProfileHeader.kt, IconCollectionRow.kt, ProfileScreen.kt, ProfileViewModel.kt, ProfileRoute.kt, OtherProfileScreen.kt, OtherProfileViewModel.kt, OtherProfileRoute.kt, ProfileViewModelTest.kt) |
| Modified files | AppNavHost.kt, KudosRoute.kt, AllKudosRoute.kt, ViewKudoRoute.kt, SendKudosBar.kt, LocaleProvider.kt, strings.xml × 2 |
| Total LOC (new) | ~700 |

---

## Overall Assessment

Solid feature delivery for a mock-data screen pair. State-hoisting pattern, Route/Screen/ViewModel layering, and the vararg `appString` overload are all consistent with existing sibling screens. The nav arg encoding strategy (`Uri.encode`) is correct for Vietnamese/spaced names. Three issues require attention before this ships: a missing `popUpTo` on all `onProfileTab` navigations that will accumulate back-stack entries on bottom-nav taps; a `forEach` inside a `verticalScroll` Column that precludes lazy rendering; and `AppNavHost.kt` exceeding the 200-line file-size convention. Everything else is low-priority style or minor DRY observations.

---

## Critical Issues

None.

---

## High Priority

### H1 — `onProfileTab` missing `popUpTo` — back-stack accumulation on bottom-nav taps

**File:** `AppNavHost.kt` — every `onProfileTab` lambda (7 occurrences)  
**Severity:** warning

Every peer bottom-nav action clears intermediate routes before navigating:
- `onSaaTab` → `popUpTo(HOME) { inclusive = true }`
- `onAwardsTab` → `popUpTo(HOME) { inclusive = false }`
- `onKudosTab` → `popUpTo(KUDOS) { inclusive = false }`

`onProfileTab` has only `launchSingleTop = true` with no `popUpTo`. A user drilling Home → Kudos → AllKudos → ViewKudo → Profile accumulates four destinations below Profile. Pressing the system back-button from Profile correctly unwinds into the previous screen rather than landing at Home. This is unexpected bottom-nav behavior and inconsistent with the established pattern.

Recommended fix (for each `onProfileTab` in a non-Profile composable):
```kotlin
onProfileTab = {
    navController.navigate(Routes.PROFILE) {
        popUpTo(Routes.HOME) { inclusive = false }
        launchSingleTop = true
    }
}
```

---

### H2 — `forEach` inside `verticalScroll` Column for kudos list

**File:** `ProfileScreen.kt:92`, `OtherProfileScreen.kt:92`  
**Severity:** warning

Both profile screens eagerly compose every `KudoCard` in a `Column + verticalScroll`. Mock data is 5 items, so it does not matter today. The sibling `AllKudosScreen` uses `LazyColumn` with `key = { it.id }` for the same card type. If the real backend ever returns more kudos (or the mock set grows for testing), this layout will compose and measure all cards unconditionally, causing jank on mid-range devices.

The screens should be consistent with `AllKudosScreen`. Converting the inner `Column` to a `LazyColumn` with `nestedScrollConnection` (or restructuring to a single `LazyColumn` with the hero section as a header item) would match the sibling pattern and future-proof the layout. This is a minor concern with mock-only data but a real concern once live data lands.

---

## Medium Priority

### M1 — `AppNavHost.kt` exceeds 200-line file-size convention

**File:** `AppNavHost.kt` — 374 lines  
**Severity:** suggestion

Project convention: keep files under 200 lines. `AppNavHost.kt` is 374 lines and growing; adding the two profile composables pushed it well past the limit. The standard mitigation is to extract route groups into extension functions (e.g., `NavGraphBuilder.kudosGraph(...)`, `NavGraphBuilder.profileGraph(...)`).

---

### M2 — `CONTENT_INSET = 20.dp` duplicated across three profile files

**Files:** `ProfileScreen.kt:37`, `OtherProfileScreen.kt:46`, `ProfileHeader.kt:34`  
**Severity:** suggestion

`CONTENT_INSET` is a file-private `val` repeated identically in all three files. The same value also appears in several kudos screen files (established pattern in this project). This is low-risk since they are all 20.dp, but a single package-internal constant would prevent accidental divergence. Note: this is a pre-existing pattern in the codebase, so fixing it in new files only may introduce asymmetry — address at the codebase level or leave consistent.

---

### M3 — `kudos_eyebrow` reused as profile KUDOS section eyebrow

**Files:** `ProfileScreen.kt:80`, `OtherProfileScreen.kt:88`  
**Severity:** suggestion

Both profile KUDOS sections use `appString(R.string.kudos_eyebrow)` ("Sun* Annual Awards 2025") as the eyebrow above "KUDOS". This is intentional per design (the Kudos section on the profile belongs to the same awards context), but it creates a dependency on a string owned by the Kudos feature. If the Kudos eyebrow label ever changes in a future design iteration, the profile screens would change too without any explicit connection. A profile-specific `profile_kudos_eyebrow` string (with the same value) would decouple them.

---

### M4 — `ProfileBadge.id` field unused at runtime

**File:** `Profile.kt:11`, `ProfileRepository.kt:23,45`  
**Severity:** suggestion (YAGNI)

`ProfileBadge.id` is constructed (`"own-$it"`, `"other-$i"`) but never read — `BadgeSlot` is keyed by `index` in `forEachIndexed`. If badge detail navigation remains a no-op TODO, the `id` field is dead weight. Remove it or document that it is reserved for the future badge-detail route.

---

## Low Priority

### L1 — `ProfileRepository.otherReceivedKudos` is identical to `receivedKudos`

**File:** `ProfileRepository.kt:54`  
**Severity:** suggestion

`otherReceivedKudos` is assigned `KudosRepository.allKudos.take(5)`, which is exactly the same slice as `receivedKudos`. This means liking a kudo on the own-profile screen and then opening another person's profile shows the same kudo in the already-liked state (they share the same underlying `Kudo` object instances). In a mock-data context this is harmless, but it is worth a comment or using a different slice (`.drop(3).take(5)`) to avoid confusion during visual QA.

---

### L2 — `ReceivedCountPill` defined in `OtherProfileScreen.kt` — could be in `IconCollectionRow.kt` or a shared file

**File:** `OtherProfileScreen.kt:132`  
**Severity:** suggestion

`ReceivedCountPill` is a simple styled `Text` composable. It lives in the `OtherProfileScreen` file which is otherwise a full-screen composable. It could equally live in a small `profile/components` file alongside similar profile chips, but this is a style preference — the current placement is not wrong.

---

### L3 — `ProfileViewModel.toggleLike` updates both `_received` and `_sent` regardless of filter

**File:** `ProfileViewModel.kt:53-55`  
**Severity:** suggestion (intentional, could use a comment)

`toggleLike` applies `toggleLikeIn` to both `_received` and `_sent`. This is correct — the same kudo can appear in the received list of one user and the sent list of the current user; toggling the heart on one list should keep the state when switching filter tabs. The comment in the file says "so the state survives a filter switch" which covers the rationale. No action required, but worth noting positively.

---

## Edge Cases Found

### E1 — Nav arg `nullable = false` on `ARG_NAME` / `ARG_CODE`: `savedStateHandle` returns `""` on decode failure

**File:** `OtherProfileViewModel.kt:25-26`, `AppNavHost.kt:317-318`

`NavType.StringType` with no `nullable = true` and no `defaultValue` will throw if the argument is absent from the back-stack entry. `Uri.encode` correctly encodes spaces and Vietnamese characters, and `NavType.StringType` auto-decodes percent-encoded strings, so the round-trip is correct for names like "Huỳnh Dương Xuân Nhật". The ViewModel fallback `?: ""` is safe for the empty-name edge case — results in a profile with an empty name string rather than a crash. Acceptable for mock data; real backend should validate non-empty.

### E2 — `kudo?.let` null guard in `ViewKudoRoute` — tapping sender/receiver while kudo is null is silently swallowed

**File:** `ViewKudoRoute.kt:29-30`

If `kudo` state is null (initial load), tapping the sender/receiver avatar fires `onOpenProfile` with empty strings rather than being blocked. The null guard (`kudo?.let { ... }`) correctly silences the call — no crash — but the navigation would still fire with empty name/code if the `let` block executes. Since `kudo` is `StateFlow<Kudo?>`, it is null only briefly at startup; this is acceptable for mock data. (Pre-existing code, not introduced in this diff.)

### E3 — `ProfileScreen` passes `onProfile = {}` no-op to `SaaBottomBar` — tapping Profile tab on own-profile does nothing

**File:** `ProfileScreen.kt:112`

This is intentional (you are already on the Profile screen), consistent with how `KudosScreen` passes `onKudos = {}`. Correct behavior.

---

## Positive Observations

- `Uri.encode` / `NavType.StringType` pairing is the right approach for Vietnamese names in path segments. Matches the existing `kudosView(id)` precedent.
- `OtherProfileViewModel` correctly uses `SavedStateHandle` for nav args, identical to `ViewKudoViewModel`. Factory injection via `viewModel()` works because Navigation Compose provides `SavedStateHandle` automatically for composable destinations with declared arguments.
- `appString(resId, vararg formatArgs)` overload is a clean, minimal addition to `LocaleProvider` — no behavioral change to existing callers.
- `KudosViewModel.toggleLikeIn` reuse across `ProfileViewModel` and `OtherProfileViewModel` correctly avoids duplicating the pure heart-toggle logic.
- `SendKudosBar` label override via optional parameter is a clean reuse — zero impact on existing callers (default value preserves original behavior).
- All new files are under 200 lines except `AppNavHost.kt` (pre-existing growth plus two new composables).
- Test coverage targets the right things: mock data correctness and the shared like-toggle logic. Tests are deterministic and use no mocks.
- `ProfileBadge.unlocked` flag drives two visually distinct rendering paths cleanly in `BadgeSlot`.
- String resources correctly use `%1$d` (integer) for `profile_received_count` and `%1$s` (string) for `profile_thank_you` — both match actual call-sites.

---

## Recommended Actions

1. **(H1 — before ship)** Add `popUpTo(Routes.HOME) { inclusive = false }` to all 7 `onProfileTab` lambdas in `AppNavHost.kt` to match `onAwardsTab`/`onKudosTab` behavior.
2. **(H2 — before real data)** Replace `forEach` + `Column` kudos lists in `ProfileScreen` and `OtherProfileScreen` with `LazyColumn` + `items(key = { it.id })`, matching `AllKudosScreen`.
3. **(M1 — cleanup sprint)** Split `AppNavHost.kt` into graph extension functions (one per feature area) to bring it under 200 lines.
4. **(M4 — optional)** Remove `ProfileBadge.id` or add a comment explaining it is reserved for future badge-detail navigation.
5. **(L1 — nice to have)** Use a different slice for `otherReceivedKudos` to avoid shared object state between own and other profile lists during visual QA.
