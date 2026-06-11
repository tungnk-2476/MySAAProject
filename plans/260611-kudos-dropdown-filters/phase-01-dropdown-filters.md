# Phase 01 — Dropdown Filters

MoMorph refs:
- Dropdown phòng ban: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/76k69LQPfj
- Dropdown hashtag: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/V5GRjAdJyb
- Clarifications: ./clarifications.md

## Overview
Priority: High · Status: Planned
Make the two Highlight filter chips functional single-select dropdowns that filter the carousel.

## Design facts (authoritative — from frame images)
- Dark, near-black anchored popup directly below the tapped chip.
- White item text, ~14sp; the active/selected item is bold.
- Small rounded corners; comfortable item height (~44dp); left-aligned labels.
- Department values: CEVC2, CEVC3, CEVC4, CEVC1, OPD, Infra.
- Hashtag values (design): #Dedicated, #Inspring (x… repeated) → deduped to #Dedicated, #Inspiring.

## Related code files
Modify:
- data/kudos/Kudo.kt — add `department: String` to Kudo.
- data/kudos/KudosRepository.kt — add `departments` + `hashtagOptions` lists; assign departments
  across the 5 highlight kudos; fix hashtag spelling to #Inspiring (already used elsewhere).
- ui/kudos/KudosViewModel.kt — filter state (selectedDepartment, selectedHashtag),
  derived `highlightKudos` (filtered), `selectDepartment`/`selectHashtag` events, pure `filterKudos` helper.
- ui/kudos/HighlightSection.kt — replace static FilterChips with FilterDropdown; carousel uses filtered list;
  add filter params + callbacks; reset pager page when the filtered list shrinks.
- ui/kudos/KudosScreen.kt — thread filter state + select callbacks through to HighlightSection.
- ui/kudos/KudosRoute.kt — collect filter state; wire select events to the ViewModel.
- ui/theme/Color.kt — add `SaaDropdownSurface` (near-black popup background).
- app/src/test/java/com/example/mysaaproject/KudosViewModelTest.kt — tests for filterKudos.

Create:
- ui/kudos/FilterDropdown.kt — anchored dark dropdown: reuses FilterChip as anchor + Material3
  DropdownMenu styled to the design; single-select; bold active item; re-select clears.

## Implementation steps
1. Kudo: add `department`. Repository: add `departments`, `hashtagOptions`; give each highlight kudo a
   department from the list; ensure hashtags use #Dedicated/#Inspiring.
2. ViewModel: hold `selectedDepartment`/`selectedHashtag` StateFlows; expose filtered `highlightKudos`
   (apply both filters, AND). Add `selectDepartment(value?)`, `selectHashtag(value?)` (re-select → clear).
   Add pure `filterKudos(list, dept, hashtag)` in companion for tests.
3. FilterDropdown.kt: `FilterDropdown(label, options, selected, onSelect)` — Box wrapping FilterChip
   (label shows selected ?: default) + DropdownMenu(dark) of items; bold the selected; tap selects+dismiss.
4. HighlightSection: use FilterDropdown for hashtag + department; render filtered carousel; guard empty state.
5. KudosScreen + KudosRoute: pass options, selected values, and onSelect callbacks down/up.
6. Color: add `SaaDropdownSurface`.
7. Tests: filter by dept only / hashtag only / both / re-select clears.

## Todo
- [x] Data: department field + repository lists + distribution
- [x] ViewModel: filter state + filtered flow + pure helper
- [x] FilterDropdown component (dark popup, single-select, bold active)
- [x] HighlightSection wiring + filtered carousel (pager reset keyed on filter selection)
- [x] KudosScreen + KudosRoute threading
- [x] Color token (SaaDropdownSurface)
- [x] Unit tests for filterKudos (4 cases)
- [x] Compile + emulator screenshot vs design

## Success criteria
- Both dropdowns open as dark anchored popups with correct values.
- Selection filters the carousel and updates the chip; popups dismiss; filters compose.
- Compiles clean; filterKudos tests pass; screenshot matches design.

## Risks
- DropdownMenu default surface is light → must override to dark (SaaDropdownSurface).
- Pager state when filtered list size changes → clamp current page.
- Material icons unavailable on classpath → reuse existing ic_arrow_down (already used by FilterChip).
