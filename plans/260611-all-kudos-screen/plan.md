# Plan — Sun*Kudos All Kudos Screen

MoMorph refs:
- All Kudos: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/j_a2GQWKDJ
- Clarifications: ./clarifications.md

## Goal
Build the dedicated "All Kudos" screen — the target of the existing "View all Kudos" link: a top bar
(back + centered title), the "ALL KUDOS" section header, a scrollable LazyColumn of the existing
KudoCard, and the shared bottom nav (Kudos active).

## Scope
Reuse existing components (KudoCard, SectionHeader, SaaBottomBar, TopBar idiom). New route + screen +
small ViewModel + a mock list. No backend, mock-only. Main-thread work (bg UI subagents disabled here).

## Phases
- [x] Phase 01 — All Kudos screen (data + ViewModel + Screen + Route + nav wiring + strings + test) — DONE
  See: phase-01-all-kudos-screen.md

## Status: COMPLETE (2026-06-11)
Compiles (BOM 2026.02.01); 9 unit tests pass; emulator-verified — View-all opens the screen, layout
matches design, like 1.000→1.001 (red heart), back returns to Kudos. Reviewer 8.5/10; H1 (robust Kudos-tab
nav) + H2 (independent-like-state KDoc) + E1 (empty-state TODO) applied.

## Key dependencies
- Existing: KudoCard.kt, SectionHeader.kt, SaaBottomBar.kt, KudosViewModel.toggleLikeIn,
  NotificationsScreen TopBar pattern, AppNavHost Routes, KudosRoute (onViewAll), KudosRepository.

## Definition of done
- Tapping "View all Kudos" on the Kudos screen opens the All Kudos screen.
- Screen shows back bar + "ALL KUDOS" header + scrollable KudoCard list + bottom nav (Kudos active).
- Back arrow returns to Kudos; like-toggle works locally.
- Compiles; test passes; emulator screenshot matches the design.
