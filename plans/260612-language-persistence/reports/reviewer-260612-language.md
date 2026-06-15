## Code Review Summary

### Scope
- Files: LanguageRepository.kt (new), AppLanguage.kt, AppRoot.kt, AppLanguageTest.kt (modified)
- LOC: ~100 total across all changed files
- Focus: DataStore persistence for in-app language; loading sentinel; hook ordering; layering; DRY/KISS

### Overall Assessment
Solid, minimal change. The Flow<AppLanguage>-with-null-initialValue loading sentinel is
correct and well-reasoned. No security, auth, or data-loss issues. Two minor issues worth
fixing: a missing `const` on the key constant (inconsistency with the existing pattern; also
slightly wastes memory on every access), and an architectural note on layering. Everything
else is clean.

---

### Critical Issues
None.

---

### High Priority
None.

---

### Medium Priority

**M1 — Missing `const` on `KEY_LANGUAGE` (warning)**

`LanguageRepository.kt:30`
```kotlin
val KEY_LANGUAGE = stringPreferencesKey("app_language")   // current
```
`SessionRepository.kt:35` has the identical pattern:
```kotlin
val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
```
Neither uses `const`. `stringPreferencesKey` returns an object at runtime so it cannot be
`const val`, but the existing `SessionRepository` pattern is the canonical reference here —
both are consistent with each other and with what `preferencesDataStore` requires. No change
needed; this is a non-issue. Marking medium only because it looks like a stylistic question
at a glance, but on inspection both repos are already identical. Resolved: no action needed.

**M2 — Layering: `LanguageRepository` under `ui/locale/` (suggestion)**
`LanguageRepository` manages DataStore I/O — that is data-layer work. Placing it under
`ui/locale/` breaks the `data/` convention established by `SessionRepository` (lives in
`data/session/`). The current placement is pragmatically acceptable given the small project
size and the tight coupling to `AppLanguage`/`LocaleProvider`, but if the project grows,
this will be the odd one out when someone searches for "where are the repositories?"
Recommendation: consider `data/language/LanguageRepository.kt` in a future cleanup, keeping
`AppLanguage.kt` and `LocaleProvider.kt` in `ui/locale/` and adding an import.

---

### Low Priority

**L1 — No test for `LanguageRepository.setLanguage` / `language` Flow (suggestion)**
`AppLanguageTest` covers only `fromTag`. The repository's DataStore interaction has no unit
or instrumented test. Given that `setLanguage`/`language` are a single-key read/write pair
with no branching, the risk is low, but a robolectric or instrumented test with a test
DataStore would close the gap if coverage targets increase.

**L2 — Language not reset on logout (informational)**
`onLogout` in `AppRoot` clears the session but does NOT reset the language to DEFAULT.
After logout, the next user who logs in on the same device will see the previous user's
language. This is almost certainly intentional (language is a device preference, not a
per-user preference), but it is undocumented. A one-line comment in `AppRoot.onLogout`
would prevent a future contributor from "fixing" it incorrectly.

---

### Edge Cases Checked

**Loading sentinel correctness — CONFIRMED CORRECT**
`languageRepository.language` is typed `Flow<AppLanguage>` (non-null): `fromTag` always
returns a non-null `AppLanguage`, so the flow never emits null. The only null in the
system comes from `collectAsStateWithLifecycle(initialValue = null)`, which intentionally
widens the type to `AppLanguage?`. This means `currentLanguage == null` is exclusively
true during the window before the first DataStore read completes. Once the flow emits, it
will always be a valid `AppLanguage`. No flash of wrong language possible; no flash of
wrong start screen possible (the `loggedIn == null` guard gates that as well).

**Hook ordering — CORRECT**
All composable hooks (`remember`, `collectAsStateWithLifecycle`, `rememberNavController`) are
called unconditionally before the `if (loggedIn == null || currentLanguage == null) return`
early exit (lines 30-36 vs line 42). This satisfies the Rules of Hooks: no hooks after
conditional returns, no conditional hooks. Compliant.

**DataStore name collision — NONE**
`SessionRepository` uses `preferencesDataStore(name = "session")`;
`LanguageRepository` uses `preferencesDataStore(name = "settings")`. Different backing files
(`session.preferences_pb` vs `settings.preferences_pb`). Extension property names
(`sessionDataStore` vs `settingsDataStore`) are also distinct. No conflict.

**Single-instance guarantee — CORRECT**
Both datastores are defined as `private val Context.xxxDataStore by preferencesDataStore(…)`
— the Kotlin `by` delegate with the `preferencesDataStore` delegate factory guarantees a
single DataStore instance per process. Using `context.applicationContext` in `AppRoot` for
both repositories ensures the same application context is passed regardless of which
Activity triggers recomposition.

**Context leak — NONE**
`LanguageRepository(context.applicationContext)` — application context, not Activity context.
Consistent with `SessionRepository`.

**Immediate switch update — CONFIRMED**
`onLanguageSelected` calls `scope.launch { languageRepository.setLanguage(it) }`, which
writes to DataStore. The `language` Flow in `AppRoot` is collected with
`collectAsStateWithLifecycle`, so any DataStore emit triggers recomposition. `ProvideAppLanguage`
receives the new value → `remember(language, baseContext)` key changes → new
`localizedContext` created → all `appString()` callers recompose. Immediate, no Activity
restart.

**Logout regression — NONE**
`onLogout` in `AppRoot:50-53` calls `sessionRepository.clearSession()` and
`NotificationsRepository.reset()`. This path is unchanged from pre-diff. The language
repository is not touched (see L2 above — intentional). `isLoggedInFlow` will emit `false`
→ recomposition → `startDestination = Routes.LOGIN`. Correct.

---

### Positive Observations
- Clean mirror of the `SessionRepository` pattern — minimal API surface, no overengineering.
- `fromTag` null/unknown fallback is defensive and correctly defaults to VN without throwing.
- All composable hooks hoisted above the early return — hook ordering compliance is explicit.
- `applicationContext` used consistently — no context leak risk.
- Two separate DataStore files (names "session" and "settings") — no key namespace collision
  even if they ever share a future DataStore instance.
- Test cases cover all `fromTag` branches including empty string and unknown locale tag.

---

### Recommended Actions
1. (Optional, low urgency) Add a comment in `AppRoot.onLogout` explaining that language is
   intentionally not cleared on logout (device-level preference, not per-user).
2. (Future) Move `LanguageRepository` to `data/language/` when doing the next round of
   architectural cleanup to match `SessionRepository`'s layering.
3. (Future) Add a robolectric instrumented test for `LanguageRepository` if coverage targets
   are introduced.

### Metrics
- Type Coverage: 100% in changed files (no `Any`, no untyped lambdas)
- Test Coverage: `fromTag` fully covered; DataStore I/O not covered (low risk)
- Linting Issues: 0 observable

### Unresolved Questions
None.
