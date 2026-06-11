# Clarifications — Search Sunner Screen

MoMorph refs:
- Search Sunner: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/3jgwke3E8O

## Session 2026-06-11

Design is clear and well-spec'd (6 specs + screen overview), no test cases. Decisions from the design + specs:

- Q: Entry point? → A: The Kudos header search icon (onSearch, currently no-op) navigates here.
- Q: Realtime search results? → A: Out of scope — the design only provides the default/Recent state, no
  results UI. The search field is an editable input (placeholder "Search Sunner"); building a results view
  would mean inventing undesigned UI (against MoMorph rules). Filtering/results = TODO.
- Q: Recent list interactivity? → A: ✕ removes the item from the recent list immediately (ViewModel state,
  no confirm, per spec). Tapping an item → profile (no Profile screen yet → no-op TODO). "View all" → no-op
  TODO (list already shows the mock items; max 5).
- Q: Recent item data? → A: Reuse the existing `Recipient` model (avatar + name + unit); mock `recentSearches`
  list in KudosRepository.
- Q: Bottom nav active tab? → A: SAA (matches the design — SAA 2025 is highlighted in the frame).
