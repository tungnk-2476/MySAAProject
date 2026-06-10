# Notifications Screen (/notification) Implementation

**Source:** MoMorph `[iOS] Notifications` — screenId `_b68CBWKl5`, fileKey `9ypp4enmFmdK3YAFJLIu6C`
**Clarifications:** [clarifications.md](clarifications.md)
**Target:** Android Jetpack Compose (mirrors Login/Home feature conventions)

## Goal
Add a Notifications screen reachable from the Home header bell: top bar (back + title) →
"mark all read" action → scrollable list of typed notification rows (icon + text + relative
time + unread dot). Reading items updates a **shared** unread count that drives the Home bell badge.

## Decisions (from clarifications)
- **Shared in-memory `NotificationsRepository`** (singleton) holds the list + read flags; Home
  and Notifications both observe it → bell badge updates live (TC_NOTIF_FUN_001/002).
- Tapping a notification **marks it read + no-op TODO** per-type navigation (targets don't exist).
- Data = stub repo seeded with the **7 mock notifications from the design** (Vietnamese content as shown).
- Relative time stored as the design's literal strings (KISS; no timestamp math for mock data).

## Phases
| # | Phase | Status |
|---|-------|--------|
| 01 | [Notifications screen + shared unread state + nav wiring](phase-01-notifications.md) | ✅ Complete (compiled, tested, emulator-verified, reviewed 8/10) |

## Key dependencies
- Compose Material3, navigation-compose, lifecycle-viewmodel-compose — all present.
- Reuses: `Montserrat`, `Saa*` colors, `appString`, `Routes`/`AppNavHost`, `keyvisual_bg`,
  `ic_heart`, `ic_pencil`, `ic_arrow_outward`.

## Out of scope
Per-type detail screens (kudo detail, secret box, profile, community standards, admin review);
real API/push; exact icon artwork (hand-authored vectors tinted to the spec's named colors).
