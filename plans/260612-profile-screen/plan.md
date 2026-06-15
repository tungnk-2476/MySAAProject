# Profile Screen — Implementation Plan

**Source:** MoMorph file `9ypp4enmFmdK3YAFJLIu6C` (SAA 2025).
- Own: `[iOS] Profile bản thân` — `hSH7L8doXB`
- Other: `[iOS] Profile người khác` — `bEpdheM0yU`

**Goal:** Fill the bottom-nav Profile tab (currently TODO) with the user's own profile, plus a
viewable other-person profile reached by tapping a sender/receiver on any kudo card.

**Approach:** Maximise reuse. Both screens compose existing pieces — `HomeHeader`, `SaaBottomBar`
(`BottomTab.PROFILE`), `KudosStatsBlock` (own stats — exact match), `FilterDropdown`, `KudoCard`,
`Avatar`, `SectionHeader`, `keyvisual_bg`. Only the profile hero, icon-collection row, and the
other-profile "thank-you" CTA are new. Mock data extracted from the design (no backend, like siblings).

## Screen anatomy
| Region | Own (hSH7L8doXB) | Other (bEpdheM0yU) |
|--------|------------------|--------------------|
| Hero | avatar + name + CEVC3 + "Legend Hero" | same + "Rising Hero" |
| Icon collection | 6 **locked** dark circles | 6 **unlocked** named badges |
| Below hero | **stats card** + "Mở Secret Box" | **"Gửi lời cảm ơn…" CTA** → Send Kudo |
| KUDOS list | dropdown filter Đã nhận/Đã gửi | static "Đã nhận N kudos" |
| Bottom nav | Profile active | Profile active |

## Phases (Track A = UI, Track B = data/logic — parallel-runnable)
- **phase-01-profile-data** (B) — COMPLETE: `data/profile/Profile.kt` + `ProfileRepository.kt` implemented with mock data extracted from MoMorph designs; UserProfile, ProfileBadge, HeroLevel reuse achieved.
- **phase-02-shared-ui** (A) — COMPLETE: `ProfileHeader.kt`, `IconCollectionRow.kt` (locked/unlocked badge variants) coded per MoMorph specs.
- **phase-03-own-profile** (A) — COMPLETE: `ProfileScreen.kt`, `ProfileViewModel.kt`, `ProfileRoute.kt` wired; bottom-nav Profile tab fully functional.
- **phase-04-other-profile** (A) — COMPLETE: `OtherProfileScreen.kt`, `OtherProfileViewModel.kt`, `OtherProfileRoute.kt` wired; reachable from kudo-card sender/receiver taps.
- **phase-05-navigation** (integration) — COMPLETE: `Routes.PROFILE` + `Routes.profileOther(name,code)` added; 6 `onProfileTab` entries wired (with popUpTo Home); kudo-card sender/receiver linked across Kudos/AllKudos/ViewKudo → other profile; other-profile CTA → Send Kudo screen; back-stack validated (H1 fix: clearBackStack on Home nav applied).
- **phase-06-strings** — COMPLETE: en + vi profile_* string resources added; SendKudosBar label override; appString vararg overload.

## Out of scope (no-op TODO, per clarifications)
Avatar tap, icon-badge tap, Secret Box flow, Copy Link, language switcher behavior.

## Success criteria — ALL MET
- ✓ Profile tab opens own profile; tapping a kudo sender/receiver opens that person's profile.
- ✓ Pixel-faithful to MoMorph specs (MoMorph designs validated on-device).
- ✓ `compileDebugKotlin` + `assembleDebug` pass without errors.
- ✓ Unit tests pass: ProfileViewModelTest added and verified.
- ✓ Reviewer report approved (H1 back-stack fix applied; H2 noted as consistent with KudosScreen pattern).
- ✓ Visual validation on-device: own profile + other-person profile both verified; navigation from kudo-card sender/receiver tap confirmed functional.

## Implementation Status
**SHIPPED** — All 6 phases complete. Implementation compiles, tests pass, visual validation on-device complete. Reviewer + tester signed off (see `plans/260612-profile-screen/reports/reviewer-260612-profile.md`).

**Deviation logged:** Home "logout via Profile tab" placeholder was replaced (logout plumbing retained with @Suppress pending designed sign-out action). No impact on Profile Screen feature scope.
