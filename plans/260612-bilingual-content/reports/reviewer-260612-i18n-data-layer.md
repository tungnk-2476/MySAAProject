---
name: reviewer-260612-i18n-data-layer
description: Review of cross-cutting VN/EN i18n change — data-layer StringRes migration for Awards, Kudos, Notifications
metadata:
  type: review
---

# Code Review — Bilingual i18n Data-Layer Migration

**Date:** 2026-06-12
**Scope:** Awards, Kudos, Notifications data + UI layers; strings.xml EN + VI
**LOC reviewed:** ~1 896 (17 files)
**Build status:** compileDebugKotlin + assembleDebug pass per task description

---

## Overall Assessment

The core architecture is sound: localizable prose is pushed down to `@StringRes Int` at the model boundary, resolved via `appString()` only at render time, and the `ProvideAppLanguage` / `LocalLocalizedContext` composition-local correctly shadows the Activity locale. The migration covers all three declared domains (Awards, Kudos, Notifications) and the EN↔VI string key set is almost perfectly in parity.

Two concerns are material before production or before the detail screens accept real API data.

---

## Critical Issues

**None** — no crashes, data leaks, or auth bypasses found.

---

## High Priority

### H1 — `Award.quantityUnit` / `prizeValue` default to `0`; `appString(0)` throws `Resources.NotFoundException` at runtime

**File:** `data/awards/Award.kt` lines 17–18
```kotlin
@param:StringRes val quantityUnit: Int = 0,
@param:StringRes val prizeValue: Int = 0,
```

`AwardInfoBlock` (AwardSections.kt lines 63, 65) calls `appString(award.quantityUnit)` and `appString(award.prizeValue)` unconditionally. Android's `Resources.getString(0)` throws `NotFoundException` — not a graceful fallback.

**Current exposure:** all three `MOCK_AWARDS` fill both fields, so no crash today.
**Future exposure:** any code path that creates an `Award` without these fields (a future API response mapper, a unit-test helper, a new mock) will crash the detail screen the moment that award is selected.

**Fix:** Guard the render site or change the sentinel:
```kotlin
// Option A — change sentinel in Award.kt so the API contract is explicit
@param:StringRes val quantityUnit: Int = Resources.ID_NULL,  // = 0, but documents intent
@param:StringRes val prizeValue: Int = Resources.ID_NULL,

// Option B — guard at render in AwardInfoBlock (safe without requiring callers to change)
val unitStr = if (award.quantityUnit != 0) appString(award.quantityUnit) else ""
val priceStr = if (award.prizeValue != 0) appString(award.prizeValue) else ""
```

Option B is the safer short-term fix with no callers to update.

---

### H2 — `AwardHighlightBlock` dropdown selection is fragile: index-based label lookup breaks if two awards share the same resolved name

**File:** `ui/awards/AwardSections.kt` lines 41–47
```kotlin
val options = awards.map { appString(it.name) }
val selectedLabel = appString(selected.name)
FilterDropdown(
    selected = selectedLabel,
    onSelect = { label -> awards.getOrNull(options.indexOf(label))?.let(onSelect) },
)
```

`List.indexOf` returns the **first** matching index. If two awards ever resolve to the same translated label, the second one becomes unreachable: tapping it always selects the first.

**Current exposure:** the three mock awards have identical EN and VI names (`Top Talent`, `Top Project`, `Top Project Leader`) — distinct today. No crash.

**Future risk:** real API data with generic names (e.g. `"Team Award"` twice) would silently mis-select.

**Recommended fix:** pass `Award` objects into a typed dropdown rather than materialising label strings in the picker logic, or match by id instead of by label:
```kotlin
// AwardHighlightBlock — wire by Award index, not by resolved label string
FilterDropdown(
    options = options,
    selected = selectedLabel,
    onSelect = { label ->
        val idx = options.indexOf(label)
        awards.getOrNull(idx)?.let(onSelect)
    },
)
```
This is the current code — the `getOrNull` guards against -1 already. The real fix is to avoid string-based matching entirely; switching to a typed variant of `FilterDropdown` (or passing index directly) would eliminate the fragility class.

---

## Medium Priority

### M1 — Unused imports: `stringResource` left in two files

**Files:**
- `ui/awards/AwardSections.kt` line 22: `import androidx.compose.ui.res.stringResource`
- `ui/kudos/SendKudoScreen.kt` line 27: `import androidx.compose.ui.res.stringResource`

Neither file calls `stringResource(...)` anywhere — confirmed by grep. These are dead imports from the pre-migration state. No runtime impact, but they read as a signal that migration is incomplete for anyone skimming the file, and any future contributor may reach for the import and use the wrong function.

**Fix:** remove both import lines.

---

### M2 — `@param:StringRes` vs `@StringRes` annotation-target inconsistency

**File:** `ui/kudos/SendKudoViewModel.kt`
- `SendKudoUiState.title`: annotated `@param:StringRes` (correct for constructor parameter)
- `onSelectTitle(title: Int)`: annotated `@StringRes` (bare, no target qualifier)

In Kotlin, `@StringRes` on a function parameter without a `@param:` qualifier **does** attach to the parameter use-site in Kotlin but the Lint tool recognises both forms. Still, the inconsistency is a mild style smell and could confuse anyone relying on IDE lint to catch callers passing non-resource integers.

Consistent form: use `@param:StringRes` on constructor parameters (data classes) and `@StringRes` on function parameters. The current usage happens to be exactly that split — but it could be worth a KDoc note to clarify intent.

---

### M3 — `app_name` missing from `values-vi/strings.xml` (only difference between the two files)

**Finding:** diff of all resource keys shows `app_name` is present in `values/strings.xml` but absent in `values-vi/strings.xml`. This is intentional (brand name stays `MySAAProject` in all locales) but it makes the two files appear subtly out-of-parity to a future maintainer.

**Impact:** none today; `app_name` is resolved from the default resource set.
**Suggestion:** add a comment in `values-vi/strings.xml` (or an explicit duplicate) so future reviewers understand the omission is deliberate.

---

## Low Priority / Suggestions

### L1 — `"x2"` fire-bonus badge in `KudosStatsBlock.kt` line 80 is a hardcoded literal

`FireBadge()` renders `Text("x2", ...)`. This is a multiplier glyph (numeric format, not prose), so it arguably belongs in strings if the value could vary, but as a fixed design decoration it is low risk. Suggestion: if the multiplier is ever dynamic, make it a format string.

### L2 — `"$count KUDOS"` in `SpotlightBoard.kt` line 56

`Text("$count KUDOS", ...)` — "KUDOS" is a brand word per the task brief (excluded from localization). No action needed; noted for completeness.

### L3 — `"KUDOS"` in `KudosBanner.kt`, `KudosPageBanner.kt`, `AwardKudosPromo.kt`

Same brand-word exclusion; no action needed.

### L4 — Pagination counter `"${pagerState.currentPage + 1}/${kudos.size}"` in `HighlightSection.kt` line 118

Numeric pagination indicator — not prose, no localization needed.

### L5 — `"✕"`, `"✓"`, `"‹"`, `"›"`, `"B"`, `"I"`, `"S"`, `"1."`, `"❝"`, `"/"` literals in `SendKudoSections.kt`

These are explicit UI glyphs / toolbar characters. Task brief excludes glyphs. No action needed.

---

## Edge Cases Found

1. **`Award` created via copy with `quantityUnit = 0` or `prizeValue = 0`** — not currently possible from the ViewModel (it only calls `selectAward(id)` which maps to `MOCK_AWARDS`), but the data class constructor makes it valid. The `AwardInfoBlock` crash path (H1) is latent, not theoretical.

2. **`options.indexOf(label)` returns -1** — `getOrNull(-1)` returns `null` and `?.let(onSelect)` is a no-op, so no crash. The UX consequence is the dropdown appears to accept a selection but the detail block does not update. Acceptable with current mock names; fragile with real data.

3. **`SendKudoUiState.title = null` with `@param:StringRes`** — `title` is correctly `Int?` (nullable Int), and `validate()` tests `s.title == null`. The `TitleSelector` renders `appString(R.string.send_kudo_award_hint)` when `selected == null`, guarding the path. No crash.

4. **All 3 `MOCK_AWARDS` always populated with non-zero `quantityUnit`/`prizeValue`** — confirms no current crash exposure from H1, but the guard should exist at the render site regardless.

---

## Positive Observations

- `ProvideAppLanguage` composition-local pattern is architecturally clean: context shadowing without Activity restart, no global state mutation.
- All `appString()` call sites correctly sit inside `@Composable` scope — no illegal calls from ViewModels or repository code.
- `SendKudoViewModel.titleOptions: List<Int>` and `TitleSelector(options: List<Int>)` are a tight, type-safe interface; the title is compared by resource id (identity), not by resolved string, so selection is immune to the label-collision bug that affects `AwardHighlightBlock`.
- EN/VI string key parity is excellent: only `app_name` differs (intentionally).
- All seven `NotificationItem` seeds, all three `Award` seeds, all `GiftRecipient` seeds, and `kudo_title_*` / `kudo_sample_content` are properly moved to resource ids.
- `@param:StringRes` on data-class constructor parameters is the correct Kotlin annotation target.

---

## Recommended Actions (prioritised)

1. **(H1 — fix before real data)** Guard `appString(award.quantityUnit)` and `appString(award.prizeValue)` in `AwardInfoBlock` for the `id == 0` case; or change the default sentinel to make the zero-id case explicit.
2. **(H2 — fix before real data)** Replace label-string-based dropdown selection in `AwardHighlightBlock` with id-based or index-based selection to eliminate label-collision fragility.
3. **(M1 — fix immediately)** Remove unused `import androidx.compose.ui.res.stringResource` from `AwardSections.kt` and `SendKudoScreen.kt`.
4. **(M3 — optional)** Add a comment in `values-vi/strings.xml` explaining `app_name` is intentionally omitted.

---

## Metrics

| Metric | Value |
|---|---|
| Files reviewed | 17 |
| LOC reviewed | ~1 896 |
| `stringResource()` calls remaining in ui/ | 0 (2 unused imports only) |
| EN/VI key parity gaps | 1 (`app_name`, intentional) |
| Zero-id StringRes crash paths exposed today | 0 (all mock awards populated) |
| Zero-id StringRes crash paths latent | 2 (`quantityUnit`, `prizeValue` defaults) |
| Hardcoded user-visible prose in ui/ (non-glyph, non-brand) | 0 |

---

## Unresolved Questions

- Should `Award.quantityUnit` and `prizeValue` remain optional (default 0) once a real API backend is wired, or will all awards always carry these fields? The answer determines whether Option A or B for H1 is preferred.
- Is `FilterDropdown` expected to stay a `List<String>` interface long-term, or will it be genericised when real API categories are added? If genericised, H2 resolves naturally.
