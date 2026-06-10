# Home Screen (/home) Implementation

**Source:** MoMorph `[iOS] Home` — screenId `OuH1BUTYT0`, fileKey `9ypp4enmFmdK3YAFJLIu6C`
**Clarifications:** [clarifications.md](clarifications.md)
**Target:** Android Jetpack Compose (mirrors existing Login feature conventions)

## Goal
Replace the placeholder `HomeScreen` with a pixel-faithful, scrollable Home dashboard:
fixed header → hero (ROOT FURTHER + live countdown + event info + ABOUT buttons) →
theme paragraph → Awards section (horizontal cards, stateful) → Kudos section →
FAB → fixed bottom nav bar.

## Decisions (from clarifications)
- Off-screen navigation → hoisted **no-op callbacks + TODO** (only /home in scope).
- Countdown → **real timer** to configurable date (default 2025-12-26); elapsed → hide "Coming soon", show `00`.
- Awards/Kudos data → **stub repository** with `Loading/Success/Empty/Error` + Retry; mock data from design; default Success.
- Behaviors → `isKudosAvailable=true`, notification badge (unread>0), FAB double-tap guard — simple state flags.

## Phases
| # | Phase | Status |
|---|-------|--------|
| 01 | [Home screen UI + state + navigation wiring](phase-01-home-screen.md) | ✅ Complete (compiled, tested, emulator-verified) |

## Key dependencies
- Compose Material3 (bundled core icons), navigation-compose, lifecycle-viewmodel-compose — all already present.
- Reuses: `Montserrat`, `Saa*` colors, `appString`, `Routes`/`AppNavHost`, `keyvisual_bg`, `logo_root_further`, `logo_sun_award`.

## Out of scope
Awards/Kudos/Search/Notifications/Profile/WriteKudo/AwardDetail screens; real API; exported
decorative rasters (award orbs, Kudos banner → styled placeholders).
