# Phase 01 — Search Sunner Screen

MoMorph refs:
- Search Sunner: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/3jgwke3E8O
- Clarifications: ./clarifications.md

## Overview
Priority: High · Status: Planned
A Sunner search screen (default/Recent state) reached from the Kudos header search icon.

## Design facts (authoritative — frame image + 6 specs)
- Top row: back chevron (left) + a dark search field with placeholder "Search Sunner" (spans the rest).
- "Recent" bold label (left) + "View all" text-link (right).
- Recent list: each row = circular Avatar + name (e.g. "Dương Huỳnh Xuân Nhật") + unit ("CECV1") + ✕ remove
  on the right. Max 5; tap row → Profile (no Profile screen → no-op). ✕ removes immediately, no confirm.
- Bottom: shared SaaBottomBar (SAA tab highlighted in the design).

## Related code files
Create:
- ui/kudos/SearchSunnerViewModel.kt — `query` + `recent: StateFlow<List<Recipient>>`; onQueryChange, removeRecent(id).
- ui/kudos/SearchSunnerScreen.kt — Box(keyvisual bg + scrim) → Column[ SearchTopBar(back + field), Recent
  header + View all, LazyColumn of recent rows ] + SaaBottomBar(SAA).
- ui/kudos/SearchSunnerRoute.kt — connects ViewModel → screen; nav callbacks.

Modify:
- data/kudos/KudosRepository.kt — add `recentSearches: List<Recipient>` (reuse Recipient; 2–3 mock items).
- ui/navigation/AppNavHost.kt — add Routes.KUDOS_SEARCH + composable; wire KudosRoute(onOpenSearch) →
  navigate(KUDOS_SEARCH); SearchSunnerRoute onBack = popBackStack; bottom-bar SAA → HOME, Kudos → KUDOS, etc.
- ui/kudos/KudosRoute.kt — add `onOpenSearch: () -> Unit`; onSearch = onOpenSearch.
- res/values/strings.xml + values-vi/strings.xml — add `search_sunner_hint` ("Search Sunner"),
  `search_recent` ("Recent"), `search_view_all` ("View all").
- app/src/test/java/com/example/mysaaproject/KudosViewModelTest.kt (or new) — removeRecent removes by id.

## Implementation steps
1. Repository: `recentSearches` mock list (reuse Recipient).
2. SearchSunnerViewModel: MutableStateFlow recent (seeded) + query; removeRecent(id) filters out the item;
   onQueryChange updates query (no results view — out of scope).
3. SearchSunnerScreen: SearchTopBar = Row[ back IconButton + dark search field (OutlinedTextField, white text,
   muted border, "Search Sunner" placeholder) ]; Recent header Row[ "Recent" bold + "View all" link ];
   LazyColumn(recent, key=id) of RecentRow(Avatar + name/unit + ✕). Empty list → just header.
4. SearchSunnerRoute: collect recent + query; removeRecent wired; onItemClick/onViewAll no-op TODO; nav callbacks.
5. Nav route + KudosRoute onOpenSearch wiring + strings + test.

## Todo
- [x] Repository recentSearches mock list (own id namespace rs1/rs2)
- [x] SearchSunnerViewModel (query + recent + removeRecent)
- [x] SearchSunnerScreen (top bar + search field + Recent + list + bottom nav, SAA active)
- [x] SearchSunnerRoute
- [x] Nav route + KudosRoute onOpenSearch wiring
- [x] Strings (en + vi)
- [x] removeRecent test
- [x] Compile + emulator screenshot vs design

## Success criteria
- Search icon opens the screen; back returns; ✕ removes a recent item; layout matches the design.
- Compiles clean; test passes.

## Risks
- Dark search field styling (vs the cream FormTextField) — style an OutlinedTextField for the dark backdrop.
- Realtime search results intentionally omitted (undesigned) — field is input-only; document the TODO.
