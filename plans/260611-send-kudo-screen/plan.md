# Plan — Sun*Kudos Send Kudo (New Kudo) Screen + Dropdowns

MoMorph refs:
- Recipient dropdown: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/5MU728Tjck
- Hashtag dropdown: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/aKWA2klsnt
- Clarifications: ./clarifications.md

## Goal
Build the New Kudo form — reached from the Kudos send bar — with: top bar (back + "New Kudo"), a cream
form panel (recipient search dropdown, Danh hiệu title selector, message + static toolbar, hashtag
multi-select dropdown+chips, image add/remove strip, anonymous toggle, nickname), and Huỷ/Gửi đi actions.

## Scope
Fully interactive form via a ViewModel (single source of truth for all fields + validation). Two dropdowns:
recipient (single-select, type-to-search) and hashtag (multi-select, max 5). Mock data, no backend.
Main-thread (bg UI subagents disabled here). Files kept <200 lines (form split across focused files).

## Phases
- [x] Phase 01 — Send Kudo screen (data + ViewModel + form UI + dropdowns + nav + strings + tests) — DONE
  See: phase-01-send-kudo-screen.md

## Status: COMPLETE (2026-06-11)
Compiles (BOM 2026.02.01); 15 unit tests pass; emulator-verified — send bar opens the form; recipient
search→select fills the field; hashtag multi-select shows ✓ + removable chips; danh hiệu selector works;
image add shows a tile; valid Gửi đi returns to Kudos. Reviewer DONE_WITH_CONCERNS; applied H2 (atomic
validate), M1 (nickname asterisk only when anonymous), M5 (split file <200 lines), L1 (internal helpers).
Declined M4 (#High-perorming is the literal design value) and M3 (hardcoded dropdown widths — minor edge).

## Key dependencies
- Existing: SaaBottomBar, Avatar, FilterChip/dropdown idiom, SaaDropdownSurface, Notifications TopBar idiom,
  AppNavHost Routes, KudosRoute (onSendKudos), KudosRepository.

## Definition of done
- Kudos send bar opens the New Kudo screen.
- Recipient search shows a dark result dropdown (avatar+name+unit), single-select fills the field.
- "+ Hashtag" opens a dark checklist dropdown (multi-select, max 5); selections show as removable chips.
- Danh hiệu selector picks a title; message editable; images add/remove (placeholders, max 5); anonymous
  toggle + nickname; Gửi đi validates and returns to Kudos; Huỷ returns.
- Compiles; ViewModel validation tests pass; emulator screenshots match both dropdown designs.
