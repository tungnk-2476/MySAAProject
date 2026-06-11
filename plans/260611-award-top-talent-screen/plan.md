# Plan — Sun*Kudos Award Top Talent (Award Detail) Screen

MoMorph refs:
- Award Top Talent: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/c-QM3_zjkG
- Clarifications: ./clarifications.md

## Goal
Build the Award Detail screen (Awards tab): sticky header → KUDOS banner → highlight block (title +
category dropdown) → award info block (badge, title, description, quantity, prize) → Sun* Kudos promo
block ("Chi tiết" → Kudos) → sticky bottom nav (Awards active). Selecting a category swaps the info block.

## Scope
Reuse HomeHeader, KudosPageBanner, SaaBottomBar, FilterDropdown. New Award model + mock repository +
ViewModel (awards list + selection) + screen (split into scaffold + sections) + route. Mock-only, no API
(no loading/error). Wire the Awards tab app-wide. Main-thread.

## Phases
- [x] Phase 01 — Award Top Talent screen (data + ViewModel + screen + sections + route + nav + icons + strings + test) — DONE
  See: phase-01-award-top-talent-screen.md

## Status: COMPLETE (2026-06-11)
Compiles (BOM 2026.02.01); 19 unit tests pass; emulator-verified — Awards tab opens the screen, layout
matches the design, category dropdown swaps the info block (Top Project → 5 Dự án / new badge & description),
stats + Kudos promo render, Awards tab active. Reviewer DONE_WITH_CONCERNS; applied M1 (Awards tab popUpTo
HOME — no back-stack accumulation), M2 (FilterDropdown widthIn + chip ellipsis), H1 (guarded empty awards),
L4 (no-op comment). Declined L1 (onAwardDetails is (Award)->Unit — wrapper required). Reused Award model
(extended), HomeHeader, KudosPageBanner, SaaBottomBar, FilterDropdown.

## Key dependencies
- Existing: HomeHeader, KudosPageBanner, SaaBottomBar (BottomTab.AWARDS), FilterDropdown, NotificationsRepository
  (unread badge), AppNavHost Routes, KudosRoute/HomeRoute (onAwardsTab).

## Definition of done
- Awards tab (from Home + Kudos family) opens the screen; Awards tab shown active.
- Category dropdown lists awards; selecting one updates badge/title/description/quantity/prize.
- "Chi tiết" → Kudos; header search/bell + other tabs navigate; content scrolls under the sticky header/nav.
- Compiles; ViewModel selection test passes; emulator screenshot matches the design.
