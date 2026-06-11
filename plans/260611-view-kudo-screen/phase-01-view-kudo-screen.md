# Phase 01 — View Kudo (detail) Screen

MoMorph refs:
- View Kudo: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/T0TR16k0vH
- Clarifications: ./clarifications.md

## Overview
Priority: High · Status: Planned
A single-kudo detail screen reached from any card's "Xem chi tiết" action.

## Design facts (authoritative — frame image + specs, B.3/B.4/F.2)
- Top bar: back chevron + centered title "Kudo".
- One expanded cream KudoCard, in order:
  sender↔receiver (avatars/names/codes/hero badges + arrow) → divider → time → **centered** title (bold,
  uppercase) → **full** message body (no truncation) → **attached-images strip** (row of ~5 thumbnails,
  max 5 per spec) → hashtags (wraps) → divider → action bar (hearts/Copy Link/Xem chi tiết).
- Bottom: shared SaaBottomBar with Kudos active.

## Related code files
Create:
- ui/kudos/ViewKudoScreen.kt — Box(keyvisual bg + scrim) → Column[ TopBar("Kudo"), verticalScroll(card), SaaBottomBar(KUDOS) ].
- ui/kudos/ViewKudoViewModel.kt — reads `kudoId` from SavedStateHandle; `kudo: StateFlow<Kudo?>` from
  KudosRepository.findById; `toggleLike()` reusing KudosViewModel.toggleLikeIn.
- ui/kudos/ViewKudoRoute.kt — connects ViewModel → screen; threads nav callbacks.

Modify:
- data/kudos/Kudo.kt — add `imageCount: Int = 0` (attached-image count for the detail strip).
- data/kudos/KudosRepository.kt — set imageCount (5) in sampleKudo; add `findById(id): Kudo?` (searches all lists).
- ui/kudos/KudoCard.kt — add `centerTitle: Boolean = false` and `imageCount: Int = 0`; render the
  placeholder image strip (between content and hashtags) when imageCount > 0; center the title when set.
- ui/navigation/AppNavHost.kt — add Routes.KUDOS_VIEW + composable("kudos_view/{kudoId}") with navArgument;
  wire KudosRoute(onOpenKudo) and AllKudosRoute(onOpenKudo) → navigate("kudos_view/$id"); ViewKudoRoute onBack = popBackStack.
- ui/kudos/KudosRoute.kt — add `onOpenKudo: (String) -> Unit`; onDetails = { onOpenKudo(it.id) }.
- ui/kudos/AllKudosRoute.kt — add `onOpenKudo: (String) -> Unit`; onDetails = { onOpenKudo(it.id) }.
- res/values/strings.xml + values-vi/strings.xml — add `kudos_view_title` = "Kudo" (both).
- app/src/test/java/com/example/mysaaproject/KudosViewModelTest.kt — findById test.

## Implementation steps
1. Kudo: add `imageCount`. Repository: sampleKudo imageCount = 5; `findById` over highlight+feed+all.
2. KudoCard: add centerTitle (TextAlign.Center + fillMaxWidth) and imageCount; ImageStrip = Row of
   `imageCount` weighted rounded placeholder tiles (gray), rendered after content, before hashtags.
3. ViewKudoViewModel(SavedStateHandle): kudo flow + toggleLike.
4. ViewKudoScreen: TopBar("Kudo") + verticalScroll card (centerTitle=true, contentMaxLines=Int.MAX_VALUE,
   imageCount=kudo.imageCount) + SaaBottomBar(KUDOS). Handle null kudo defensively.
5. ViewKudoRoute: collect kudo; onLike → toggleLike; copy/sender/receiver no-op TODO; nav callbacks.
6. Nav: KUDOS_VIEW route + arg; wire onOpenKudo in both Kudos and AllKudos routes; bottom-bar/back nav.
7. Strings + findById test.

## Todo
- [x] Kudo.imageCount + repository findById + imageCount in mock (5)
- [x] KudoCard: centerTitle + imageCount + placeholder image strip
- [x] ViewKudoViewModel (kudo flow + toggleLike, SavedStateHandle)
- [x] ViewKudoScreen (top bar + scrollable detail card + bottom bar)
- [x] ViewKudoRoute
- [x] Nav route (kudoId arg) + onOpenKudo wiring (Kudos + AllKudos)
- [x] Strings (en + vi)
- [x] findById test
- [x] Compile + emulator screenshot vs design
- [x] Review fixes: shared applyLikeToggle helper (H1), overflow derived from maxLines (L1)

## Success criteria
- "Xem chi tiết" opens the correct kudo's detail; back returns.
- Centered title, full content, 5-tile image strip, hashtags, working like-toggle.
- Compiles clean; test passes.

## Risks
- KudoCard gains optional params — keep list/carousel call-sites unchanged (defaults preserve current look).
- ViewModel nav-arg via SavedStateHandle requires the composable scope (navigation-compose default factory) — standard.
- Long content needs vertical scroll inside the screen (card can exceed viewport).
