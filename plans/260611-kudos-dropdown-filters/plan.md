# Plan — Sun*Kudos Dropdown Filters (Department + Hashtag)

MoMorph refs:
- Dropdown phòng ban: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/76k69LQPfj
- Dropdown hashtag: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/V5GRjAdJyb
- Clarifications: ./clarifications.md

## Goal
Wire the two existing Highlight-section filter chips ("Hashtag", "Phòng ban") to open
anchored dark dropdown popups (matching the design). Single-select; choosing a value
filters the Highlight Kudos carousel against mock data and reflects the selection on the chip.

## Scope
This is a wiring + small-component task on the existing Kudos screen — NOT a new screen.
No backend. Mock-data only. Background UI subagents are disabled in this workspace
(per project memory), so all work runs on the main thread.

## Phases
- [x] Phase 01 — Dropdown filters (data + ViewModel filter state + FilterDropdown component + wiring + tests) — DONE
  See: phase-01-dropdown-filters.md

## Status: COMPLETE (2026-06-11)
Compiles (BOM 2026.02.01); 7 unit tests pass; emulator-verified — dept CEVC3→1/1, re-select clears→1/5,
hashtag #Inspiring→1/4, like on page 2 keeps page (no pager jump). Reviewer 7.5/10; H1/H2/M1 fixed.

## Key dependencies
- Existing: FilterChip.kt, HighlightSection.kt, KudosViewModel.kt, KudosRoute.kt, KudosScreen.kt,
  Kudo.kt, KudosRepository.kt, theme/Color.kt
- Compose Material3 DropdownMenu (already on classpath)

## Definition of done
- Tapping a chip opens a dark anchored popup with the design's values.
- Selecting an item filters the carousel; chip label shows the selection; popup dismisses.
- Department + hashtag filters compose (AND). Re-selecting the active item clears that filter.
- Compiles; unit tests for filter logic pass; emulator screenshot matches the design.
