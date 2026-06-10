# Phase 01 — Home Screen UI + State + Navigation

## Overview
Priority: High · Status: ✅ Complete
Build the Home dashboard as a presentational tree driven by a `HomeViewModel`, mirroring the
Login feature's ViewModel/Route/Screen split. All visual values come from MoMorph node specs.

## Design facts (authoritative — frame 375w, content inset 20dp)
- **Header** (fixed): logo (left) · actions row (right): language switcher (flag+VN+arrow) · search · bell+badge.
- **Hero** (`mms_2_content`, gap 32dp): ROOT FURTHER logo (reuse `logo_root_further`) · countdown block · ABOUT AWARD / ABOUT KUDOS buttons.
  - Countdown digit box: 32×56dp, radius 8dp, 0.5dp border `#FFEA9E`, white→transparent gradient, opacity 0.5; two digits/unit gap 4dp; unit 72×84 incl. label; units DAYS/HOURS/MINUTES.
  - Event info: "Thời gian: 26/12/2025", "Địa điểm: Âu Cơ Art Center", "Tường thuật trực tiếp tại Group Facebook Sun* Family".
  - Buttons: gold `#FFEA9E`, 160×40dp, radius 4, padding 12, gap 8, label + ↗ icon.
- **Theme paragraph** (`mms_3_note`): multi-line white body text.
- **Awards** (`mms_4`): SectionHeader (gold label "Sun* Annual Awards 2025" + title "Hệ thống giải thưởng" + divider) → horizontal `LazyRow` of cards 160×298dp (gap 12): image placeholder + name + 3-line truncated desc + "Chi tiết ↗" text link. States: Loading / Success / Empty / Error(+Retry).
- **Kudos** (`mms_5`): SectionHeader ("Phong trào ghi nhận" / "Sun* Kudos") → banner 335×145 (dark + gold accent + "KUDOS") → "ĐIỂM MỚI CỦA SAA 2025" badge → description → gold "Chi tiết ↗" button. Hidden entirely when `isKudosAvailable=false`.
- **FAB** (`mms_6`): bottom-right 89×48, gold glow `#FAE287`; pencil icon + Sun*/Kudos icon; double-tap guarded.
- **Bottom nav** (`mms_7`, fixed, 375×72): SAA 2025 (Home, active gold) · Awards (trophy) · Kudos (heart) · Profile (person). Active = gold `#FFEA9E`, inactive = reduced-opacity white.

## Files to create (ui/home/, ≤200 lines each)
- `HomeViewModel.kt` — `HomeUiState` (countdown ticks via flow, `AwardsState`, `isKudosAvailable`, `unreadCount`); `retryAwards()`.
- `HomeRoute.kt` — connects VM + hoisted nav callbacks → `HomeScreen`.
- `HomeScreen.kt` — composition root: background keyvisual (top, fading) + fixed header + scrolling content + FAB + fixed bottom bar. Preview.
- `HomeHeader.kt` — header bar (reuse `LanguageSelector`).
- `CountdownTimer.kt` — countdown row (digit boxes + unit labels) + "Coming soon".
- `HeroSection.kt` — logo + countdown + event info + ABOUT buttons.
- `SectionHeader.kt` — reusable gold-label + title + divider.
- `AwardsSection.kt` — header + LazyRow + Loading/Empty/Error states.
- `AwardCard.kt` — single card.
- `KudosSection.kt` — header + banner + badge + desc + button.
- `KudosBanner.kt` — styled placeholder banner.
- `HomeFab.kt` — floating action button.
- `HomeBottomBar.kt` — bottom navigation.
- `PillButton.kt` — reusable gold label+icon button (ABOUT AWARD/KUDOS, Kudos Chi tiết).

## Files to create (data/awards/)
- `Award.kt` — model (id, name, description).
- `AwardsRepository.kt` — stub: `suspend fun loadAwards(): Result<List<Award>>` returning design mock data.

## Files to modify
- `AppNavHost.kt` — `HomeScreen` → `HomeRoute`; pass `onLogout` + no-op nav callbacks.
- `strings.xml` (+ values-vi) — Home strings (coming_soon, days/hours/minutes, event date/venue/livestream, section labels, theme paragraph, kudos badge/desc, nav labels, about labels, chi tiet, retry).
- `Color.kt` — add `SaaGoldGlow` (#FAE287) and nav inactive if needed.
- New vectors: `ic_arrow_outward.xml`, `ic_trophy.xml`.

## Todo
- [x] strings + colors + vector drawables (8 icon vectors hand-authored; Material icons not on classpath)
- [x] Award model + stub repository
- [x] HomeViewModel (countdown flow + awards state)
- [x] Reusable: PillButton, SectionHeader
- [x] Header, Hero, CountdownTimer
- [x] AwardsSection + AwardCard (+ states)
- [x] KudosSection + KudosBanner
- [x] HomeFab + HomeBottomBar
- [x] HomeScreen root + HomeRoute + AppNavHost wiring
- [x] Compile, unit test (HomeCountdownTest), visual verify on emulator
- [x] Reviewer pass (7.5/10); applied N1/N2/N4/N5, declined false-positive M1

## Success criteria
- Compiles; app launches to Home after login; layout matches design; countdown ticks; Awards states + Retry work; Kudos toggles; badge toggles; FAB double-tap guarded; bottom-nav active = SAA 2025.
- Unit test for countdown computation (incl. past-date → zero + Coming soon hidden).

## Risks
- Material core icons availability → fallback: add `material-icons-core` dep or custom vectors.
- Decorative rasters unavailable → styled placeholders (flagged).
