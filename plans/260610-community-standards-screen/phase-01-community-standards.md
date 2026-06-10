# Phase 01 — Community Standards Screen + Nav Wiring

## Overview
Priority: Medium · Status: ✅ Complete
A purely presentational, scrollable static screen. All visual values + copy from MoMorph node specs.

## Design facts (authoritative — frame 375w, content inset 20dp)
- **Top nav** (375×42): back icon (left) + centered title "Tiêu chuẩn chung" (17sp Medium, white).
- **Background**: keyvisual BG + dark scrim (legibility), like Notifications.
- **Banner** (`KV`, 375×64, inset 20): ROOT FURTHER art → reuse `logo_root_further`.
- **Content frame** (inset 20, gap 12): section B → divider (`#2E3940`) → section C.
- **Section B — Community Standards** (`mms_B`):
  - Title "Tiêu chuẩn cộng đồng" — 18sp Bold, gold `#FFEA9E`.
  - Intro paragraph — 14sp Bold, gold; lineHeight 20.
  - Warning paragraph — 14sp Normal, white.
  - 10 numbered violation criteria — 14sp Normal, white (rendered `1.`–`10.`).
- **Section C — Security Standards** (`mms_C`):
  - Title "Tiêu chuẩn bảo mật" — 18sp Bold, gold.
  - Description — 14sp Bold, white.
  - 2 info points ("Bảo mật Thông tin", "Phạm vi Chia sẻ") — 14sp, white (bullets).
  - Support contact ("Liên hệ Hỗ trợ … Slack duong.thi.thuy.an") — 14sp Bold, gold.

## Files to create
**ui/standards/**
- `CommunityStandardsScreen.kt` — top bar + keyvisual/scrim + scrolling content (private `SectionTitle`, `NumberedList`, `BulletPoint` helpers). Preview. Kept ≤200 lines via string resources + a criteria string-array loop.

## Files to modify
- `AppNavHost.kt` — `Routes.COMMUNITY_STANDARDS`; `composable` → `CommunityStandardsScreen(onBack = popBackStack)`; pass `onOpenCommunityStandards = navigate(...)` into `NotificationsRoute`.
- `NotificationsRoute.kt` — add `onOpenCommunityStandards` param; wire `onCommunityStandards` (was no-op TODO).
- `strings.xml` (+ values-vi) — nav title, section titles, intro, warning, `cs_criteria` string-array (10), security description, 2 info points, support contact.

## Todo
- [x] strings (en + vi) incl. 10-item criteria array (R.array.cs_criteria) + appStringArray helper
- [x] CommunityStandardsScreen (top bar, banner, sections, numbered list, bullets)
- [x] Route + Notifications link wiring (AppNavHost, NotificationsRoute onOpenCommunityStandards)
- [x] Compile, regression tests pass, emulator-verified from Notifications link
- [x] Reviewer 7.5/10; applied M1 (ContentScale import), M2 (bullet width), M3 (shared cd_back); H1 (Montserrat Bold font asset) noted as known limitation
- [x] Post-review fix: ROOT FURTHER banner sized to design 151×64 + 24dp top gap (logo/padding bug)

## Success criteria
- Notifications "Tiêu chuẩn cộng đồng" link opens this screen; back returns.
- Layout matches design (banner, two sections, numbered list, divider); scrolls.
- Compiles; existing tests still pass; emulator-verified.

## Risks
- Long localized copy → keep in resources; criteria as a string-array to stay DRY and under the line budget.
