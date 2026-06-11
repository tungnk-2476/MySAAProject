# Plan — Sun*Kudos View Kudo (detail) Screen

MoMorph refs:
- View Kudo: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/T0TR16k0vH
- Clarifications: ./clarifications.md

## Goal
Build the Kudo detail screen — the target of the existing "Xem chi tiết"/onDetails callback: top bar
(back + "Kudo"), one expanded cream card (sender↔receiver, time, centered title, full message,
placeholder image strip, hashtags, action bar), and the shared bottom nav. Reached per-kudo via a nav arg.

## Scope
Reuse KudoCard (extended with optional centerTitle + imageCount) + SaaBottomBar + Notifications TopBar
idiom. New route (with kudoId arg) + screen + small ViewModel. Mock-only, main-thread.

## Phases
- [x] Phase 01 — View Kudo screen (data + lookup + ViewModel + Screen + Route + KudoCard extension + nav + strings + test) — DONE
  See: phase-01-view-kudo-screen.md

## Status: COMPLETE (2026-06-11)
Compiles (BOM 2026.02.01); 10 unit tests pass; emulator-verified — "Xem chi tiết" opens the tapped kudo's
detail (centered title, full content, 5 placeholder tiles), like 1.000→1.001, back returns to Kudos.
Reviewer 7.5/10; applied H1 (shared pure applyLikeToggle helper) + L1 (overflow derived from maxLines).
Declined M2 (would blank the strip) and M3 (popUpTo already pops KUDOS_ALL; inclusive=false preserves Kudos state).

## Key dependencies
- Existing: KudoCard.kt, SaaBottomBar.kt, KudosViewModel.toggleLikeIn, AppNavHost Routes,
  KudosRoute/AllKudosRoute (onDetails), KudosRepository.

## Definition of done
- "Xem chi tiết" on any card (Kudos carousel/feed + All Kudos) opens the detail for that kudo.
- Detail shows the card with centered title, full content, a 5-tile placeholder image strip, hashtags, actions.
- Back returns to the previous screen; like-toggle works locally.
- Compiles; tests pass; emulator screenshot matches the design.
