# Sun* Kudos Screen Implementation

**Source:** MoMorph `[iOS] Sun*Kudos` — screenId `fO0Kt19sZZ`, fileKey `9ypp4enmFmdK3YAFJLIu6C`
**Clarifications:** [clarifications.md](clarifications.md)
**Target:** Android Jetpack Compose (mirrors Home/Notifications conventions)

## Goal
The Kudos hub reached from the bottom-nav "Kudos" tab: header → KV banner → Send-Kudos pill →
Highlight (filters + swipeable carousel of kudo cards) → Spotlight Board (static) → All Kudos
(stats + Secret Box + Top-10 recipients + feed + View all) → bottom nav (Kudos active).

## Decisions (from clarifications)
- **Visual-faithful + light interaction**: mock data; working carousel (HorizontalPager, center-active/faded sides) + pagination; **local heart like-toggle**; scrollable stats/top-10/feed.
- **Spotlight Board = static placeholder** ("388 KUDOS" + scattered names + non-interactive search bar).
- **Display + no-op TODO** for: Hashtag/Phòng ban filters (dropdown chips only), Send Kudos, Mở Secret Box, "Xem chi tiết", "View all Kudos", "Copy Link", avatar/name taps, hashtag taps.
- Data = in-memory mock repository (highlight kudos, all-kudos feed, stats, top-10 recipients, spotlight).
- Bottom nav generalized into a shared `SaaBottomBar` (active-tab param) reused by Home + Kudos.

## Phases
| # | Phase | Status |
|---|-------|--------|
| 01 | [Kudos screen + shared bottom bar + nav wiring](phase-01-kudos-screen.md) | ✅ Complete (compiled, tested, emulator-verified, reviewed 7.5/10) |

## Key dependencies
- Compose `HorizontalPager` (foundation), Material3, navigation-compose.
- Reuses: `Montserrat`, `Saa*` colors, `appString`, `Routes`/`AppNavHost`, `keyvisual_bg`,
  `logo_sun_award`, `ic_heart`, `ic_arrow_outward`, `ic_arrow_down`, `ic_pencil`, `ic_search`, header (`HomeHeader`).

## Out of scope
Backend/real data; kudo-detail/profile/send-kudos/secret-box/view-all/awards/profile screens (no-op TODO);
filter bottom sheets; like business rules (x2 day, sender-disable); interactive network chart; image attachments.
