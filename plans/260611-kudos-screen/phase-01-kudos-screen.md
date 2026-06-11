# Phase 01 — Kudos Screen + Shared Bottom Bar + Nav Wiring

## Overview
Priority: High · Status: ✅ Complete
Presentational screen + ViewModel + Route (mirrors Home). Visual-faithful with mock data and the two
real interactions: carousel swipe + local heart toggle. All visual values from MoMorph node specs.

## Design facts (authoritative — frame 375w, content inset 20dp)
- **Header**: reuse `HomeHeader` (logo · lang · search · bell+badge) over keyvisual+scrim.
- **A — KV Kudos banner**: tagline "Hệ thống ghi nhận và cảm ơn" + large Sun* "KUDOS" wordmark (reuse `logo_sun_award` + gold "KUDOS" text, like Home's KudosBanner).
- **A.1 — Send Kudos pill**: pencil icon + "Hôm nay, bạn muốn gửi kudos đến ai?", rounded input-style, gold-ish border.
- **B — Highlight**: SectionHeader ("Sun* Annual Awards 2025" / "HIGHLIGHT KUDOS") + filter chips (Hashtag ▾, Phòng ban ▾) + `HorizontalPager` carousel of mock kudo cards (center page full, side pages scaled+faded) + pagination "n/5" with ‹ › arrows.
- **KudoCard** (reused in carousel + feed): cream card; sender↔receiver row (avatars = initial circles, name, code "CECV10", hero badge pill "Rising/Legend Hero", arrow between); time "10:00 - 10/30/2025"; bold title "IDOL GIỚI TRẺ"; content (max 3 lines carousel / 5 feed, ellipsis); hashtags (max 5, ellipsis); divider; hearts ("1.000" + heart icon, tappable toggle) + "Copy Link" + "Xem chi tiết ↗".
- **B.6/7 — Spotlight Board**: SectionHeader ("SPOTLIGHT BOARD") + static dark board showing "388 KUDOS" with scattered Sunner names + a non-interactive "Tìm kiếm" search bar.
- **C — All Kudos**: SectionHeader ("ALL KUDOS") + **Stats block** (Số Kudos nhận được/đã gửi, Số tim nhận được [+ x2 fire badge], Secret Box đã mở/chưa mở — value right-aligned bold) + "Mở Secret Box" gold button + **Top-10** ("10 SUNNER NHẬN QUÀ MỚI NHẤT" + avatar+name+message rows) + kudo feed (KudoCards) + "View all Kudos ↗".
- **Bottom nav**: Kudos active (gold), others reduced-opacity white.

## Files to create
**data/kudos/**
- `Kudo.kt` — `data class Kudo(id, senderName, senderCode, senderHero, receiverName, receiverHero, time, title, content, hashtags: List<String>, hearts: String, liked: Boolean)`; `enum HeroLevel { RISING, LEGEND }`.
- `KudosStats.kt`, `GiftRecipient.kt` — small mock models.
- `KudosRepository.kt` — mock highlight (5) + feed list + stats + top-10 + spotlight (count 388, names).

**ui/kudos/**
- `KudosViewModel.kt` — exposes highlight/feed/stats/recipients/spotlight; `toggleLike(id)` (local).
- `KudosRoute.kt` — VM + no-op TODO nav callbacks + bottom-nav nav → screen.
- `KudosScreen.kt` — composition root (header overlay + scroll content + SaaBottomBar). Preview.
- `KudosBanner.kt`, `SendKudosBar.kt`, `HighlightSection.kt`, `KudoCard.kt`, `SpotlightBoard.kt`, `AllKudosSection.kt`, `KudosStatsBlock.kt`, `GiftRecipientsList.kt`, `FilterChips.kt`, `Avatar.kt` (initial-circle), `HeroBadge.kt` (pill).

**ui/components/**
- `SaaBottomBar.kt` — `enum BottomTab { SAA, AWARDS, KUDOS, PROFILE }`; tab callbacks + activeTab. Replaces `HomeBottomBar`.

## Files to modify
- `HomeScreen.kt` / `HomeRoute.kt` — use `SaaBottomBar(activeTab = SAA, …)`; pass through onKudosTab. (delete `HomeBottomBar.kt`)
- `AppNavHost.kt` — `Routes.KUDOS`; `composable` → `KudosRoute`; Home `onKudosTab` → navigate(KUDOS); Kudos `onSaaTab` → navigate(HOME).
- `strings.xml` (+ values-vi) — kudos tagline, send-kudos placeholder, HIGHLIGHT/SPOTLIGHT/ALL KUDOS headers, filter labels (Hashtag/Phòng ban), stats labels, secret-box button, top-10 title, copy-link, xem chi tiết, view all, search placeholder.
- New vector: `ic_link` (copy link). Mock content (kudo text, names, hashtags) in repository.

## Todo
- [x] strings + ic_link + colors (card cream / muted border / container dark)
- [x] Kudo/KudosStats/GiftRecipient models + KudosRepository (mock)
- [x] KudosViewModel (data + toggleLike + shared unreadCount)
- [x] Shared SaaBottomBar (refactored Home off HomeBottomBar; deleted it)
- [x] Avatar, FilterChip, KudoCard (HeroBadge folded inline)
- [x] KudosPageBanner, SendKudosBar, HighlightSection (HorizontalPager carousel), SpotlightBoard, KudosStatsBlock, GiftRecipientsList, AllKudosSection
- [x] KudosScreen + KudosRoute + AppNavHost wiring (Home Kudos tab → Kudos; Kudos SAA tab → Home)
- [x] Unit test: KudosViewModelTest (toggleLike flips liked + heart ±1; formatHearts)
- [x] Compile, test, emulator-verify (Home → Kudos tab; carousel swipe + pagination; like toggle red)
- [x] Reviewer 7.5/10; fixed C-1 (remember-in-conditional), H-1 (rename banner), H-2 (move SectionHeader→components), M-4 (chevron tap target), N-1 (typo), H-3 (LazyColumn TODO); SectionHeader now shared in ui/components

## Success criteria
- Home "Kudos" tab opens this screen; bottom nav shows Kudos active.
- Carousel swipes with center-active/faded sides + pagination.
- Heart toggles locally (gray↔red).
- All sections render with mock data; screen scrolls; compiles; tests pass; emulator-verified.

## Risks
- Screen size → strict file decomposition (≤200 lines each); KudoCard is the heaviest — keep helpers small.
- HorizontalPager API (foundation pager) — verify import/version at compile.
- Avatars/badges/network art not exportable → initial-circles + text pills + static board (flagged).
