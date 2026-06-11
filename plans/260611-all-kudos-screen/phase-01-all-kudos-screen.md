# Phase 01 — All Kudos Screen

MoMorph refs:
- All Kudos: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/j_a2GQWKDJ
- Clarifications: ./clarifications.md

## Overview
Priority: High · Status: Planned
A full-screen list of KudoCards reached from the Kudos screen's "View all Kudos" link.

## Design facts (authoritative — frame image + specs)
- Top bar: back chevron (left) + centered title "All Kudos" (17sp medium), on the dark keyvisual backdrop.
- Section header: eyebrow "Sun* Annual Awards 2025" + title "ALL KUDOS".
- Body: vertical scroll list of the cream KudoCard (sender↔receiver, time, title, content ~3 lines,
  hashtags, hearts/copy/detail bar). 4 cards visible, scrollable.
- Bottom: shared SaaBottomBar with the Kudos tab active.

## Related code files
Create:
- ui/kudos/AllKudosScreen.kt — Box(keyvisual bg + scrim) → Column[ TopBar, SectionHeader, LazyColumn<KudoCard> ]
  + SaaBottomBar(active = KUDOS). Stateless/presentational; state hoisted.
- ui/kudos/AllKudosViewModel.kt — `kudos: StateFlow<List<Kudo>>` from KudosRepository.allKudos;
  `toggleLike(id)` reusing KudosViewModel.toggleLikeIn.
- ui/kudos/AllKudosRoute.kt — connects ViewModel → AllKudosScreen; threads nav callbacks.

Modify:
- data/kudos/KudosRepository.kt — add `allKudos: List<Kudo>` (8 mock items via sampleKudo).
- ui/navigation/AppNavHost.kt — add Routes.KUDOS_ALL + composable; wire KudosRoute(onOpenAllKudos) →
  navigate(KUDOS_ALL); AllKudosRoute onBack = popBackStack, onSaaTab → HOME, onKudosTab = popBackStack.
- ui/kudos/KudosRoute.kt — add `onOpenAllKudos: () -> Unit` param; pass as KudosScreen onViewAll.
- res/values/strings.xml + values-vi/strings.xml — add `kudos_all_screen_title` = "All Kudos" (both locales).

## Implementation steps
1. Repository: add `allKudos = List(8) { i -> sampleKudo("a$i", i) }`.
2. AllKudosViewModel: MutableStateFlow seeded from repo; toggleLike via shared pure helper.
3. AllKudosScreen: reuse Notifications TopBar idiom (IconButton ic_arrow_back + centered title),
   SectionHeader, LazyColumn(items, key = id) of KudoCard(contentMaxLines = 3), SaaBottomBar(KUDOS).
4. AllKudosRoute: collectAsStateWithLifecycle; wire onLike → viewModel.toggleLike; other card actions no-op TODO.
5. Nav: Routes.KUDOS_ALL; KudosRoute gains onOpenAllKudos; AppNavHost wires both.
6. Strings: kudos_all_screen_title.
7. Test: assert repository.allKudos non-empty + toggleLikeIn flips a card in that list.

## Todo
- [x] Repository allKudos mock list (8 items)
- [x] AllKudosViewModel (kudos flow + toggleLike)
- [x] AllKudosScreen (top bar + header + LazyColumn + bottom bar)
- [x] AllKudosRoute
- [x] Nav route + KudosRoute onOpenAllKudos wiring (Kudos-tab uses navigate+popUpTo per review H1)
- [x] Strings (en + vi)
- [x] Test (allKudos populated + like-toggle on that list)
- [x] Compile + emulator screenshot vs design

## Success criteria
- "View all Kudos" opens the screen; back returns to Kudos.
- List scrolls; like-toggle works; layout matches the design.
- Compiles clean; test passes.

## Risks
- KudoCard hashtag line is maxLines=1 while the design shows ~2 lines — keep the shared card as-is
  (consistency/DRY) rather than fork it; minor, acceptable deviation.
- Bottom bar + back arrow both present — wire both to sensible destinations (no nav loops).
