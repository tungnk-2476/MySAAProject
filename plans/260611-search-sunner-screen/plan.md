# Plan — Sun*Kudos Search Sunner Screen

MoMorph refs:
- Search Sunner: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/3jgwke3E8O
- Clarifications: ./clarifications.md

## Goal
Build the Search Sunner screen — reached from the Kudos header search icon: a top bar (back + "Search
Sunner" field), a "Recent" section with a "View all" link, and a removable list of recent-search sunners
(avatar + name + unit), over the shared bottom nav.

## Scope
Reuse the Recipient model, Avatar, SaaBottomBar, and the back-bar idiom. New route + screen + small
ViewModel (query + recent list + remove). Mock data, no backend. Realtime search results out of scope
(undesigned). Main-thread.

## Phases
- [x] Phase 01 — Search Sunner screen (data + ViewModel + screen + route + nav + strings + test) — DONE
  See: phase-01-search-sunner-screen.md

## Status: COMPLETE (2026-06-11)
Compiles (BOM 2026.02.01); 16 unit tests pass; emulator-verified — header search opens the screen,
✕ removes a recent item (2→1), back returns to Kudos, layout matches the design (SAA tab active).
Reviewer 8.5/10; applied #3 (recentSearches own id namespace rs1/rs2) + #2 (reset-on-eject comment).
Realtime search results intentionally out of scope (undesigned).

## Key dependencies
- Existing: Recipient, Avatar, SaaBottomBar, AppNavHost Routes, KudosRoute (onSearch), KudosRepository.

## Definition of done
- Kudos header search icon opens the Search Sunner screen.
- Search field shows "Search Sunner" and is editable; back returns to Kudos.
- Recent list shows mock sunners (avatar + name + unit); ✕ removes an item immediately.
- Bottom nav present (SAA active per design).
- Compiles; ViewModel remove test passes; emulator screenshot matches the design.
