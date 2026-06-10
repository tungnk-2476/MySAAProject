---
name: reviewer-260610-home-screen
description: Production-readiness review of the Home dashboard feature (UI + ViewModel + data layer)
metadata:
  type: project
---

# Code Review — Home Dashboard Feature

**Date:** 2026-06-10
**Reviewer:** reviewer agent (Staff Engineer)
**Scope:** 16 files, ~1310 LOC

---

## Scope

| Layer | Files |
|---|---|
| UI | HomeScreen, HomeRoute, HomeViewModel, HomeHeader, HeroSection, CountdownTimer, SectionHeader, AwardsSection, AwardCard, KudosSection, KudosBanner, HomeFab, HomeBottomBar, PillButton |
| Data | Award, AwardsRepository |
| Nav | AppNavHost (modified) |
| Test | HomeCountdownTest |

---

## Overall Assessment

Solid architecture with a well-isolated ViewModel, correct StateFlow lifecycle, and good state machine coverage for Awards. One **major** layout bug on gesture-navigation devices and several minor issues around DRY, accessibility, and unnecessary inheritance. No security or data-leak issues. No N+1 queries.

---

## Major Issues

### M1 — HomeBottomBar: `.navigationBarsPadding()` applied BEFORE `.height(72.dp)` → nav bar eats into content on edge-to-edge devices

**File:** `HomeBottomBar.kt:43–47`

```kotlin
.background(Color(0xFF021019))
.navigationBarsPadding()   // ← adds inset padding within the composable
.height(72.dp)             // ← total height constrained to 72dp, nav inset included
```

With `enableEdgeToEdge()` active and a 3-button nav bar (~48dp), the Row has only 24dp of usable height — icons and labels are squished or clipped. On gesture-navigation devices (nav bar ~0dp) it's fine, which explains why emulator visual verification may have passed.

**Fix:** Flip the order so the 72dp content area sits above the nav bar:

```kotlin
.background(Color(0xFF021019))
.height(72.dp)
.navigationBarsPadding()   // adds space BELOW the 72dp content
```

Or, for full correctness, drop the fixed height and let content + padding drive it:

```kotlin
.fillMaxWidth()
.background(Color(0xFF021019))
.navigationBarsPadding()
.padding(horizontal = 8.dp)
// remove explicit .height(72.dp) — let NavTab weight fill naturally
```

---

## Minor Issues

### N1 — `HomeViewModel` extends `AndroidViewModel` unnecessarily

**File:** `HomeViewModel.kt:40`

`AwardsRepository()` takes no `Context`; `HomeViewModel` never calls `getApplication()`. Extending `AndroidViewModel` forces the `Application` parameter and couples the class to the Android framework without benefit.

**Fix:** Change to `class HomeViewModel : ViewModel()`. This also aligns better with future DI (Hilt/Koin).

---

### N2 — `HomeFab` double-tap guard is shared across both icons, not per-button

**File:** `HomeFab.kt:45–51`

`lastClickAt` is a single value. Tapping "Write Kudos" starts a 600ms window during which "Kudos Shortcut" tap is silently dropped (and vice versa). The plan's TC_IOS_HOME_FUN_013 describes guarding rapid double-taps on the same action, not cross-action suppression.

**Fix:** Use two independent guards or pass the action as a key:

```kotlin
var lastWriteAt by remember { mutableLongStateOf(0L) }
var lastShortcutAt by remember { mutableLongStateOf(0L) }
```

---

### N3 — `CONTENT_INSET = 20.dp` declared as `private val` in three separate files (DRY)

**Files:** `HomeScreen.kt:37`, `AwardsSection.kt:28`, `KudosSection.kt:18`

All three define the same constant independently. A drift in one file won't be caught.

**Fix:** Extract to a `HomeTokens.kt` or add to the theme as a design token, e.g.:
```kotlin
// ui/home/HomeTokens.kt
internal val HomeContentInset = 20.dp
```

---

### N4 — `AwardCard` details link: tiny touch target with no ripple indication

**File:** `AwardCard.kt:91–109`

Only the `Row` containing "Chi tiết ↗" is clickable (roughly 20dp tall). Material accessibility guidelines require minimum 48×48dp touch targets. Additionally, `.clickable(onClick = ...)` without `indication`/`interactionSource` suppresses the ripple.

**Fix:** Wrap the entire card `Column` in `.clickable(onClick = onDetailsClick)` and let the details link remain as a visual affordance only. Or add `role = Role.Button` + proper touch target extension.

---

### N5 — `HomeBottomBar` nav surface color is a hardcoded hex literal, not from theme

**File:** `HomeBottomBar.kt:45`

`Color(0xFF021019)` is the nav bar surface color. `HomeHeader.kt:87` similarly hardcodes `Color(0xFFFF3B30)` for the notification badge. These should be named tokens in `Color.kt` (e.g., `SaaSurface`, `SaaNotificationBadge`) so they can be changed from one place.

---

### N6 — `KudosBanner` gradient colors (`0xFF050608`, `0xFF1A1407`, `0xFF3A2E0A`) are inline

**File:** `KudosBanner.kt:42–44`

Same pattern as N5 — three gradient stops are not referenced by name anywhere. At minimum a `private val` named constant for the color list would help future theming.

---

## Nits

### Nit1 — Missing `@Preview` on all components except `HomeScreen`

Only `HomeScreen` has a `@Preview`. Components like `CountdownTimer`, `HomeFab`, `AwardCard`, `HomeBottomBar` have none. This makes isolated visual verification harder during future changes.

---

### Nit2 — `SectionHeader` eyebrow `SaaOnDark.copy(alpha = 0.7f)` is ad-hoc alpha

**File:** `SectionHeader.kt:39`

The 0.7f alpha is defined inline. With `SaaInactiveTab = Color(0x99FFFFFF)` (~60% alpha) already in `Color.kt`, the 70% eyebrow alpha should either use the existing token or be extracted to its own named value to avoid silent mismatches.

---

### Nit3 — `DigitBox` applies `border` modifier after `clip`, causing potential border clipping

**File:** `CountdownTimer.kt:83–84`

```kotlin
.clip(RoundedCornerShape(8.dp))
.background(...)
.border(0.5.dp, ..., RoundedCornerShape(8.dp))
```

In Compose, the `border` draw happens after `clip`, so the outermost 0.5dp of the border may be partially clipped. The standard idiom is to apply `border` before `clip`:

```kotlin
.border(0.5.dp, ..., RoundedCornerShape(8.dp))
.clip(RoundedCornerShape(8.dp))
.background(...)
```

---

### Nit4 — Unit test: no test for `retryAwards()` triggering a second load cycle or concurrent calls

**File:** `HomeCountdownTest.kt`

The countdown pure-function tests are thorough. However, `retryAwards()` (which calls `loadAwards()`) has no test verifying idempotency under rapid retry taps (state = Loading already, but `loadAwards()` doesn't guard against concurrent launches — two coroutines could be running simultaneously, both writing to `_awardsState`). Low risk now (stub repo always succeeds), but worth a note for when a real repository is wired.

---

## Edge Cases Confirmed Safe

| Scenario | Verdict |
|---|---|
| Countdown with past event date | Correct — all zeros, `isBeforeEvent = false`, "Coming soon" hidden |
| 30s remaining (< 1 min) | Correct — `isBeforeEvent = true`, minutes shows 0 |
| Config change (rotation) | Safe — `AndroidViewModel` + `WhileSubscribed(5000ms)` handles brief subscriber gap |
| Awards state machine (Loading→Success/Empty/Error→Retry) | Correct |
| `isKudosAvailable = false` hides entire Kudos section | Correct — guarded at `HomeScreen.kt:94` |
| `unreadCount = 0` hides badge dot | Correct — guarded at `HomeHeader.kt:80` |
| Countdown flow cancellation on ViewModel clear | Safe — `viewModelScope` cancels `while(true)` loop via `CancellationException` |
| Navigation back-stack on logout | Correct — `popUpTo(HOME) { inclusive = true }` cleans back stack |
| Double `statusBarsPadding` in `HomeScreen` | Not a double-apply — `HomeHeader` and `HeroSection` are sibling overlays in the same `Box`, each consuming the inset independently. Correct. |

---

## Positive Observations

- State machine for Awards (`Loading / Success / Empty / Error`) is clean and complete with a working Retry path.
- `Countdown` and `AwardsState` are declared alongside `HomeViewModel` but are simple data/sealed types with no ViewModel coupling — easy to move if needed.
- `collectAsStateWithLifecycle` used consistently in `HomeRoute` — correct lifecycle-aware collection.
- `key = { it.id }` on `LazyRow` items — correct, avoids recomposition thrash on list changes.
- `eventEpochMillis` computed once as a `companion object val` — no per-call Calendar allocation.
- All file sizes well under 200 lines (largest: `HomeScreen.kt` at 182 lines).
- `appString()` usage is correct: called at `@Composable` scope, reads from `LocalLocalizedContext`, respects in-app language switching without Activity recreation.
- `SharingStarted.WhileSubscribed(5_000L)` on the countdown `StateFlow` — appropriate 5s grace window for config changes.
- Presentational/Route/ViewModel split mirrors the Login feature pattern exactly.

---

## Recommended Actions

1. **[Major]** Fix `HomeBottomBar` modifier order: move `.height(72.dp)` before `.navigationBarsPadding()`.
2. **[Minor]** Change `HomeViewModel : AndroidViewModel` → `HomeViewModel : ViewModel`.
3. **[Minor]** Split `HomeFab` double-tap guard into per-button timestamps.
4. **[Minor]** Extract `CONTENT_INSET` and the two hardcoded surface/badge colors into shared constants.
5. **[Minor]** Expand `AwardCard` clickable area to at least 48dp or move click to the full card.
6. **[Nit]** Fix `border` before `clip` order in `DigitBox`.
7. **[Nit]** Add `@Preview` composables to `HomeFab`, `HomeBottomBar`, `CountdownTimer`, `AwardCard`.

---

## Metrics

| Metric | Value |
|---|---|
| Files reviewed | 16 |
| Total LOC | ~1310 |
| Critical issues | 0 |
| Major issues | 1 (layout bug on 3-button nav) |
| Minor issues | 5 |
| Nits | 4 |
| Unit test coverage | Countdown pure function: complete; Awards loading cycle: not tested |

---

**Status:** DONE_WITH_CONCERNS
**Summary:** Feature is functionally correct and architecturally sound. One major layout bug (`HomeBottomBar` modifier order) will cause visible UI breakage on devices with 3-button navigation bars. All other findings are minor or cosmetic.
**Concerns:** M1 (bottom bar layout) should be fixed before shipping to a broader device pool — gesture-nav devices are fine, but 3-button nav (common on older/mid-range Android) will show squished icons.
