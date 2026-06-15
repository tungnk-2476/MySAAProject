# Language Persistence (VN/EN) — Implementation Plan

**Status:** COMPLETE

**Context:** The VN/EN i18n already works app-wide (composition-local `Context` via `ProvideAppLanguage`
+ `appString`; switcher on Login + the shared header; full `values`/`values-vi` parity). The only gap:
the selected language is held in `AppRoot`'s `rememberSaveable` and is **not persisted**, so a cold
restart resets to VN.

**Goal:** Persist the language choice (DataStore, mirroring `SessionRepository`) and restore it on
launch so the whole app remembers it. Keep the existing `appString` approach unchanged.

## Changes
- `ui/locale/AppLanguage.kt` — **DONE** added `fromTag(tag: String?): AppLanguage` (maps saved locale tag → enum, defaults to VN); unit tests cover all branches (vi/en/unknown/null).
- `ui/locale/LanguageRepository.kt` — **DONE** new DataStore ("settings"): `language: Flow<AppLanguage>` + `suspend setLanguage(AppLanguage)`; mirrors `SessionRepository` pattern; loading sentinel (null initialValue) prevents render flash.
- `ui/AppRoot.kt` — **DONE** loads language from repo at startup; gates first render until session + language load complete; `onLanguageSelected` persists via `setLanguage()`; removed `rememberSaveable` state; comment added explaining language persists across logout (device preference, not per-user).
- `AppLanguageTest.kt` — **DONE** `fromTag` mapping tested (vi/en/unknown/null → correct defaults).

### Validation
- **Compile:** `assembleDebug` passes.
- **Unit tests:** all tests pass (fromTag mapping coverage).
- **On-device:** Switch to EN → force-stop → relaunch → app stays EN; cold restart persists across app restarts and logout.

## Out of scope
Per-app locale API migration; switcher placement changes; any string re-translation (coverage already complete).

## Success criteria
- ✓ Pick EN → kill app → relaunch → app is still EN (persisted), app-wide.
- ✓ `assembleDebug` compiles; unit tests pass; on-device persistence validated.

## Deferred (Low Priority)
1. **Layering:** Move `LanguageRepository` from `ui/locale/` to `data/language/` to match `SessionRepository` layering convention. Pragmatically acceptable in small projects; future cleanup recommended if codebase grows.
2. **Test coverage:** Add robolectric/instrumented test for `LanguageRepository.setLanguage()` / `language` Flow. DataStore I/O is a single-key pair with no branching (low risk); defer pending coverage target increases.
