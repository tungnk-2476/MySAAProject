# Phase 01 — Award Top Talent (Award Detail) Screen

MoMorph refs:
- Award Top Talent: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/c-QM3_zjkG
- Clarifications: ./clarifications.md

## Overview
Priority: High · Status: Planned
The Awards-tab detail screen with a category selector that swaps award content.

## Design facts (authoritative — frame image + 18 specs + 34 test cases)
- Sticky Header (logo + language + search + bell badge) — reuse HomeHeader.
- KV KUDOS banner (tagline + KUDOS wordmark) over the keyvisual artwork — reuse KudosPageBanner.
- Highlight block: sub-label "Sun* Annual Awards 2025" + title "Hệ thống giải thưởng SAA 2025" + category
  dropdown ("Top Talent" + chevron, gold border).
- Award info block: TOP TALENT badge (240dp, gold-glow ring), title row (icon + name, gold bold),
  description, divider, "Số lượng giải thưởng" (icon + 10 + Cá nhân), divider, "Giá trị giải thưởng"
  (icon + 7.000.000 VNĐ gold + cho mỗi giải thưởng).
- Sun* Kudos promo: "Phong trào ghi nhận" label + "Sun* Kudos" title (gold) + banner + "ĐIỂM MỚI CỦA SAA
  2025" badge + description + "Chi tiết" gold button (→ Kudos).
- Sticky SaaBottomBar, Awards tab active (gold).

## Related code files
Create:
- data/awards/Award.kt — `data class Award(id, name, description, quantity, quantityUnit, prizeValue)`.
- data/awards/AwardsRepository.kt — mock list (Top Talent + a few more, each with full data).
- ui/awards/AwardViewModel.kt — `awards`, `selectedAward` (default first), `selectAward(id)`; `unreadCount`.
- ui/awards/AwardScreen.kt — scaffold: Box(keyvisual bg) [ scroll content: BannerArea(KudosPageBanner) +
  AwardHighlightBlock + AwardInfoBlock + KudosPromoBlock ] + fixed HomeHeader overlay + SaaBottomBar(AWARDS).
- ui/awards/AwardSections.kt — AwardHighlightBlock (SectionHeader-ish + FilterDropdown), AwardInfoBlock
  (AwardBadge + title row + description + StatRow x2), KudosPromoBlock (label/title/banner/badge/desc/Chi tiết),
  AwardBadge, StatRow, KudosPromoBanner.
- ui/awards/AwardRoute.kt — connects ViewModel → screen; nav callbacks.
- res/drawable/ic_diamond.xml, res/drawable/ic_award.xml — hand-authored stat icons.

Modify:
- ui/navigation/AppNavHost.kt — add Routes.AWARDS + composable; wire every no-op `onAwardsTab` (Kudos,
  AllKudos, ViewKudo, SendKudo, SearchSunner) → navigate(AWARDS); AwardRoute tabs (SAA→Home, Kudos→Kudos,
  Profile→TODO), header search→Search, bell→Notifications, Chi tiết→Kudos. Add onOpenAwards to HomeRoute call.
- ui/home/HomeRoute.kt — add `onOpenAwards: () -> Unit`; wire onAwardsTab + onAwardDetails + onAboutAward to it.
- res/values/strings.xml + values-vi/strings.xml — award labels + Kudos promo copy (en + vi).
- app/src/test/java/com/example/mysaaproject/AwardViewModelTest.kt (new) — selectAward swaps selectedAward; default = first.

## Implementation steps
1. Award model + AwardsRepository mock (Top Talent w/ the design's text + 2–3 more categories).
2. AwardViewModel: awards + selectedAward StateFlow + selectAward(id) + unreadCount (from NotificationsRepository).
3. Icons: hand-author ic_diamond (quantity) + ic_award (title/value).
4. AwardSections: AwardBadge (gold ring + name), StatRow (icon + label + value + suffix), AwardHighlightBlock
   (eyebrow + title + FilterDropdown of award names), AwardInfoBlock, KudosPromoBlock (+ KudosPromoBanner placeholder).
5. AwardScreen scaffold mirroring KudosScreen (fixed HomeHeader + scroll + fixed SaaBottomBar(AWARDS)).
6. AwardRoute + nav wiring (AWARDS route; wire onAwardsTab app-wide; HomeRoute onOpenAwards) + strings + test.

## Todo
- [x] Award model (extended) + AwardsRepository mock enriched
- [x] AwardViewModel (awards + selected + select + unreadCount; guarded init)
- [x] ic_diamond + ic_award drawables
- [x] AwardSections (badge, statrow, highlight, info) + AwardKudosPromo (promo block, banner, Chi tiết)
- [x] AwardScreen scaffold + AwardRoute
- [x] Nav: AWARDS route + wire onAwardsTab app-wide (popUpTo HOME) + HomeRoute onOpenAwards
- [x] Strings (en + vi)
- [x] AwardViewModel selection tests (3)
- [x] Compile + emulator screenshot vs design

## Success criteria
- Awards tab opens the screen; dropdown swaps award content; Chi tiết → Kudos; layout matches design.
- Compiles clean; selection test passes.

## Risks
- Screen size → split scaffold/sections to stay <200 lines/file.
- Stat icons unavailable → hand-author 2 minimal vectors (memory: Material icons not on classpath).
- Badge/banner images are placeholders (no assets) — keep faithful shape (gold ring + KUDOS wordmark).
