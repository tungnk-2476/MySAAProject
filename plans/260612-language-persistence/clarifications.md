# Clarifications — Language Persistence (VN/EN)

## Session 2026-06-12
- Q: i18n already translates app-wide; what should "implement lại" deliver? → A: Persist the language via DataStore (restore on launch, app-wide); keep the current appString approach
- Finding: coverage is already complete (102 appString usages; only non-translatable glyphs hardcoded; values/values-vi parity except app_name)
- Finding: the gap is persistence — AppRoot uses rememberSaveable, not DataStore; cold restart resets to VN
- Out of scope (not chosen): per-app locale API migration; coverage audit; switcher reachability changes
