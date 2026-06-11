# Phase 01 — Send Kudo (New Kudo) Screen + Dropdowns

MoMorph refs:
- Recipient dropdown: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/5MU728Tjck
- Hashtag dropdown: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/aKWA2klsnt
- Clarifications: ./clarifications.md

## Overview
Priority: High · Status: Planned
An interactive New Kudo form reached from the Kudos send bar, with recipient + hashtag dropdowns.

## Design facts (authoritative — frame images + node tree)
- Top bar: back chevron + centered "New Kudo".
- Cream form panel "Viết KUDO", top→bottom:
  1. Panel title "Gửi lời cám ơn và ghi nhận đến đồng đội".
  2. Người nhận * — search field (chevron); typing shows a dark dropdown of results (avatar + name + unit);
     single-select fills the field.
  3. Danh hiệu * — selector field (chevron) → dropdown of award titles.
  4. Message — a static formatting toolbar row (bold/italic/strike/number/link/quote) + multiline text box
     + helper "Bạn có thể '@ + tên' để nhắc tới đồng nghiệp khác".
  5. Hashtag * — selected hashtags as removable chips (✕) + "+ Hashtag (Tối đa 5)" button → dark checklist
     dropdown (✓ on selected, multi-select, max 5).
  6. Image — row of placeholder thumbnails (each removable ✕, max 5) + "+ Image (Tối đa 5)" button.
  7. Checkbox "Gửi lời cám ơn và ghi nhận ẩn danh".
  8. Nickname ẩn danh * — text input (e.g. "Doraemon").
  9. Actions: "Huỷ ✕" + "Gửi đi ➤".
- Shared SaaBottomBar (Kudos active).

## Related code files
Create:
- data/kudos/Recipient.kt — `data class Recipient(id, name, unit)`.
- ui/kudos/SendKudoViewModel.kt — `SendKudoUiState` + events + `validate()`; mock options from repository.
- ui/kudos/SendKudoScreen.kt — scaffold: keyvisual bg + scrim, TopBar("New Kudo"), scrollable form panel
  (composing the section composables), action buttons, SaaBottomBar(KUDOS).
- ui/kudos/SendKudoSections.kt — TitleSelector, MessageField (+ static toolbar), ImageStrip (add/remove),
  AnonymousField (checkbox + nickname), ActionButtons.
- ui/kudos/SendKudoPickers.kt — RecipientField (search + dark results dropdown) + HashtagField (chips +
  dark checklist dropdown).
- ui/kudos/SendKudoRoute.kt — connects ViewModel → screen; onSent → back to Kudos; onCancel → back.

Modify:
- data/kudos/KudosRepository.kt — add `recipients`, `titleOptions`, `sendHashtagOptions` (design literal).
- ui/navigation/AppNavHost.kt — add Routes.SEND_KUDO + composable; wire KudosRoute(onSendKudo) →
  navigate(SEND_KUDO); SendKudoRoute onSent/onCancel = popBackStack; bottom-bar/SAA nav as elsewhere.
- ui/kudos/KudosRoute.kt — add `onSendKudo: () -> Unit`; onSendKudos = onSendKudo.
- res/values/strings.xml + values-vi/strings.xml — all New Kudo labels/placeholders/validation (en + vi).
- app/src/test/java/com/example/mysaaproject/KudosViewModelTest.kt (or new SendKudoViewModelTest.kt) —
  validation tests (missing recipient/title/hashtag fails; hashtag max 5; anonymous requires nickname; valid passes).

## Implementation steps
1. Recipient model + repository mock lists (recipients incl. Dương Huỳnh Xuân Nhật/Nhân CECV1; titleOptions;
   sendHashtagOptions = design list).
2. SendKudoViewModel: immutable UiState (recipientQuery, selectedRecipient, recipientResults, title, message,
   selectedHashtags, imageCount, anonymous, nickname, errors) + events; `validate()` sets errors, returns bool.
3. Pickers: RecipientField (TextField + dark results column shown while query matches & none selected;
   pick → set recipient, fill query); HashtagField (chips with ✕ remove + "+ Hashtag" anchored dark
   DropdownMenu, ✓ on selected, cap at 5).
4. Sections: TitleSelector (FilterDropdown-style picker), MessageField (toolbar icons static + multiline
   OutlinedTextField + helper), ImageStrip (placeholder tiles each w/ ✕, "+ Image" up to 5), AnonymousField
   (checkbox glyph + nickname input), ActionButtons (Huỷ outlined dark + Gửi đi gold).
5. SendKudoScreen scaffold composes the above in a verticalScroll; SendKudoRoute wires events + nav.
6. Nav route + KudosRoute onSendKudo wiring + strings + tests.

## Todo
- [x] Recipient model + repository mock lists (kudoRecipients, titleOptions, sendHashtagOptions)
- [x] SendKudoViewModel (state + events + atomic validate)
- [x] RecipientField + HashtagField dropdowns (SendKudoPickers.kt)
- [x] TitleSelector, MessageField+toolbar, ImageStrip, AnonymousField, ActionButtons (SendKudoSections.kt)
- [x] Shared helpers (SendKudoCommon.kt — internal FieldLabel/FormTextField/AddPillButton/ToolbarGlyph/ActionButton)
- [x] SendKudoScreen scaffold + SendKudoRoute
- [x] Nav route + KudosRoute onSendKudo wiring (send bar)
- [x] Strings (en + vi)
- [x] Validation unit tests (5 cases)
- [x] Compile + emulator screenshots vs both designs

## Success criteria
- Both dropdowns work (recipient single-select search; hashtag multi-select max 5 with chips).
- Gửi đi validates and returns to Kudos; Huỷ returns; form state is consistent.
- Compiles clean; validation tests pass; layout matches the designs.

## Risks
- Form size → split across SendKudoScreen/Sections/Pickers to stay <200 lines/file.
- Recipient results dropdown over a focused TextField — render inline dark column below the field (avoid
  focus-stealing DropdownMenu over text input).
- Hashtag max-5 + duplicate prevention in the toggle logic (pure, unit-tested).
