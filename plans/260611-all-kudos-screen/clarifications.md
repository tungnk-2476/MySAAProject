# Clarifications — All Kudos Screen

MoMorph refs:
- All Kudos: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/j_a2GQWKDJ

## Session 2026-06-11

Design is unambiguous (a full-screen list of the existing cream KudoCard), specs are thin (card
structure only), no test cases. Decisions made from the design + existing conventions:

- Q: New dedicated route or reuse the Kudos screen's data? → A: Dedicated route (Routes.KUDOS_ALL) with
  its own AllKudosViewModel/AllKudosRoute/AllKudosScreen, matching the Notifications screen pattern.
- Q: Entry point? → A: The existing no-op `onViewAll` ("View all Kudos") on the Kudos screen navigates here.
- Q: Card component? → A: Reuse existing KudoCard (DRY) with contentMaxLines = 3 (matches the design's truncation).
- Q: List data? → A: Mock `allKudos` list in KudosRepository (8 items via existing sampleKudo). No pagination (mock only, YAGNI).
- Q: Chrome? → A: Top bar (back arrow + centered "All Kudos" title) reusing the Notifications TopBar idiom,
  plus the shared SaaBottomBar with the Kudos tab active (both shown in the design).
- Q: Like behavior? → A: Local heart like-toggle reusing the pure KudosViewModel.toggleLikeIn helper.
