# Clarifications — View Kudo (Kudo detail) Screen

MoMorph refs:
- View Kudo: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/T0TR16k0vH

## Session 2026-06-11

- Q: How to handle the attached-images strip (no photo assets in project)? → A: Placeholder thumbnail
  tiles (count from design = 5); add an `imageCount` field to the mock Kudo data; tiles render only in the detail card.
- Q: How does the screen get its data / get reached? → A: Per-kudo via navigation — "Xem chi tiết" on any
  card passes the kudo id; ViewKudo looks it up in the repository and displays that kudo.
- Q (decided from design): Detail card = the existing KudoCard with centered title + full (untruncated)
  content + the image strip; reused (DRY) rather than forked, via new optional params.
- Q (decided): Top bar title = "Kudo" (matches design); shared SaaBottomBar with Kudos active.
- Q (decided): Like-state on the detail is screen-local (mock), independent of the list feeds — same
  documented limitation as the All Kudos screen until a shared repository/backend exists.
