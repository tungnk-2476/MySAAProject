# Community Standards Screen Implementation

**Source:** MoMorph `[iOS] Sun*Kudos_Tiêu chuẩn cộng đồng` — screenId `xms7csmDhD`, fileKey `9ypp4enmFmdK3YAFJLIu6C`
**Clarifications:** [clarifications.md](clarifications.md)
**Target:** Android Jetpack Compose (mirrors existing screen conventions)

## Goal
A static, scrollable Community Standards page reachable from the Notifications "Tiêu chuẩn cộng đồng"
inline link: top bar (back + title) → ROOT FURTHER banner → Community Standards (intro + warning +
10 numbered violation criteria) → divider → Security Standards (description + 2 info points + support contact).

## Decisions (recorded defaults — no behavioral gaps for a static page)
- Nav title = **"Tiêu chuẩn chung"** (design title node) / EN "Community standards".
- Banner reuses `logo_root_further` (the ROOT FURTHER art isn't separately exportable).
- All body content stored as **string resources** (en + vi); Vietnamese text from the design.
- **No ViewModel** — purely presentational (`onBack` only). Entry wired from the Notifications inline link.

## Phases
| # | Phase | Status |
|---|-------|--------|
| 01 | [Community Standards screen + nav wiring](phase-01-community-standards.md) | ✅ Complete (compiled, tested, emulator-verified, reviewed 7.5/10) |

## Key dependencies
- Reuses: `Montserrat`, `Saa*` colors, `appString`, `Routes`/`AppNavHost`, `keyvisual_bg`,
  `logo_root_further`, `ic_arrow_back`, `SaaDivider`.

## Out of scope
Other entry points (e.g. hidden-kudo detail); real backend. No unit test (no logic — static content).
