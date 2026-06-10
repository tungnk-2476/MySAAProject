---
date: 2026-06-10
reviewer: reviewer-agent
status: DONE_WITH_CONCERNS
score: 7.5/10
---

# Code Review — Community Standards Screen

## Scope
- New: `ui/standards/CommunityStandardsScreen.kt`
- Modified: `ui/locale/LocaleProvider.kt`, `ui/navigation/AppNavHost.kt`, `ui/notifications/NotificationsRoute.kt`
- Resources: `values/strings.xml`, `values-vi/strings.xml`
- LOC (new): ~189 lines

## Overall Assessment
The implementation is clean, well-structured, and correctly static (no ViewModel per design intent). Locale wiring via `appStringArray` is sound. Two real issues need attention before a production release: a missing bold font weight that affects visual fidelity, and a ContentScale import style inconsistency. The remaining items are minor/nit level.

---

## Critical Issues
None.

---

## High Priority

### H1 — `FontWeight.Bold` has no registered font; fake-bold synthesized at runtime
**File:** `CommunityStandardsScreen.kt` — `SectionTitle` (line 127), `Body(bold=true)` (line 138)
**Also:** `Type.kt` — `Montserrat` family (lines 12–16)

`Montserrat` FontFamily only registers Light/Normal/Medium. `FontWeight.Bold` (700) is unregistered. Android's font system will synthesize bold algorithmically from Medium (500) — the result is visibly lighter than true bold on devices that don't have the font OTA. Section titles and the intro paragraph will render slightly thinner than the design spec's "18sp Bold gold" and "14sp Bold gold" intent.

Note: `NotificationRow` pre-existing bold use is not part of this PR's surface, but this PR introduces new Bold-dependent composables.

**Fix:** Add `montserrat_semibold.ttf` or `montserrat_bold.ttf` (whichever the MoMorph design exports) to `res/font/` and register it:
```kotlin
Font(R.font.montserrat_semibold, FontWeight.SemiBold),
Font(R.font.montserrat_bold,     FontWeight.Bold),
```

---

## Medium Priority

### M1 — `ContentScale` used as fully-qualified name instead of import
**File:** `CommunityStandardsScreen.kt` line 54

```kotlin
contentScale = androidx.compose.ui.layout.ContentScale.Crop,
```

`NotificationsScreen.kt` (the reference screen) imports `ContentScale` at the top level. Inconsistent style — reviewers will notice the qualification looks like a workaround for an import conflict that doesn't exist.

**Fix:** Replace with top-level import `import androidx.compose.ui.layout.ContentScale` and use `ContentScale.Crop`.

---

### M2 — `BulletPoint` "•" has no fixed-width prefix column; visually inconsistent with `NumberedList`
**File:** `CommunityStandardsScreen.kt` lines 162–167

`NumberedList` gives the number label `Modifier.width(24.dp)` to align all item bodies. `BulletPoint` uses no width modifier on the "•" character — body alignment depends on the bullet's font-metric width. On some Montserrat variants the bullet is ~6–8dp wide, visually offset from the numbered list's consistent 24dp column.

**Fix:** Add `Modifier.width(24.dp)` (or at minimum `minWidth`) to the `RowText("•")` call, matching the numbered list pattern:
```kotlin
RowText("•", modifier = Modifier.width(24.dp))
```

---

### M3 — `notif_cd_back` string resource reused as back-button `contentDescription` in a different screen
**File:** `CommunityStandardsScreen.kt` line 110

```kotlin
contentDescription = appString(R.string.notif_cd_back),
```

This creates a coupling: the Community Standards screen's back button borrows a string scoped to Notifications. In VI it reads "Quay lại" (correct), but refactoring or renaming `notif_cd_back` would silently affect this screen. A shared `cd_back` resource (or a dedicated `cs_cd_back`) should be used.

**Fix:** Add `<string name="cd_back">Back</string>` / `"Quay lại"` to both strings.xml files and reference that instead.

---

## Minor Priority

### N1 — Scrim alpha differs between screens without a named constant
**File:** `CommunityStandardsScreen.kt` line 57 (`0.85f`) vs `NotificationsScreen.kt` line 65 (`0.82f`)

If the design intent is "same scrim", this is a magic-number inconsistency. If they intentionally differ (design exports showed slightly different darkness), document it with a comment. Neither is a runtime bug.

**Suggestion:** Extract to a `Color.kt` constant (e.g., `SaaScrimAlpha = 0.85f`) or add a comment `// design: scrim 85 % (darker than Notifications intentionally)`.

---

### N2 — `contentDescription` for the ROOT FURTHER banner is a hardcoded English string
**File:** `CommunityStandardsScreen.kt` line 71

```kotlin
contentDescription = "ROOT FURTHER",
```

TalkBack in VI will still announce "ROOT FURTHER" (English). For a decorative brand image this may be intentional, but if TalkBack should skip it, use `contentDescription = null`. If it should be announced, put it in strings.xml.

**Suggestion:** Use `null` (decorative art) or add `R.string.cs_banner_cd` with a localized description.

---

### N3 — Community Standards link Row in `NotificationRow` has no explicit a11y semantics
**File:** `NotificationRow.kt` lines 72–91

The inline link Row uses bare `.clickable` with no `Role` or `contentDescription`. TalkBack will focus the outer notification row (also `.clickable`) and may merge the two touch targets, making the link unreachable via accessibility traversal.

**Suggestion:** Add `Modifier.semantics { role = Role.Button; contentDescription = "Open community standards" }` to the link Row (use a string resource for the description).

---

## Edge Cases Verified (Clean)

- `appStringArray` empty-list guard: `forEachIndexed` on an empty list is a no-op — no crash.
- Scroll constraint chain: `Box(fillMaxSize) > Column(fillMaxSize+statusBarsPadding) > Column(fillMaxSize+verticalScroll)` — the inner Column has a fixed viewport height (screen minus status bar minus top bar); `verticalScroll` allows overflow. **Scroll works correctly.**
- `Modifier.weight(1f)` inside `Row` in `NumberedList` and `BulletPoint` — valid; `weight` is only illegal inside `Column` scope. **Correct.**
- `navigationBarsPadding` applied inside `verticalScroll` — adds spacing as part of scrollable content area, ensuring last item clears nav bar. **Correct.**
- Language composition: `ProvideAppLanguage` wraps the entire nav graph in `AppRoot`, so `LocalLocalizedContext` is available to `CommunityStandardsScreen` without an explicit `language` param. **Correct.**
- String escaping: `\"` in `<item>` elements within `cs_criteria` string-array renders as `"` at runtime. **Correct.**
- No apostrophe escaping issue in any `cs_*` string. **Clean.**
- `cs_title` ("Tiêu chuẩn chung") vs `notif_community_standards` ("Tiêu chuẩn cộng đồng") naming divergence in VI is intentional per design (nav bar title vs the document name).

---

## Positive Observations

- `appStringArray` implementation is minimal and correct — `LocalLocalizedContext.current.resources.getStringArray(resId).toList()` properly participates in the composition local, so language switches trigger recomposition without Activity recreation.
- The screen is genuinely static (no ViewModel, no side effects) — correct for read-only content per plan.
- Helper composables (`SectionTitle`, `Body`, `NumberedList`, `BulletPoint`, `RowText`) are well-extracted and keep the file under 200 lines.
- `Routes.COMMUNITY_STANDARDS` as a string constant avoids nav-graph typos.
- `launchSingleTop = true` on the navigation call prevents duplicate back stack entries from rapid taps.
- Preview wraps `ProvideAppLanguage(VN)` — renders the most common localization path in Android Studio preview.

---

## Recommended Actions (Prioritized)

1. **[H1]** Obtain and register `montserrat_bold.ttf` (or semibold) in `Type.kt` — visual fidelity for all bold text on the screen.
2. **[M1]** Replace inline `androidx.compose.ui.layout.ContentScale.Crop` with a top-level import.
3. **[M2]** Add `Modifier.width(24.dp)` to the `RowText("•")` in `BulletPoint` for alignment consistency.
4. **[M3]** Introduce a shared `cd_back` string resource; remove the Notifications-scoped coupling.
5. **[N1–N3]** Address at next polish pass; none are ship-blockers for a static informational screen.

---

**Status:** DONE_WITH_CONCERNS
**Score:** 7.5 / 10
**Primary concern:** Missing bold font weight (H1) — visual regression on all bold text. Remaining items are cosmetic or a11y polish.
