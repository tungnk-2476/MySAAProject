# Clarifications — Send Kudo (New Kudo) Screen + Dropdowns

MoMorph refs:
- Recipient dropdown: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/5MU728Tjck
- Hashtag dropdown: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/aKWA2klsnt

## Session 2026-06-11

- Q: Form interactivity & the rich-text toolbar? → A: Fully interactive form (recipient search+select,
  hashtag multi-select chips max 5, image add/remove, anonymous toggle, nickname, validation). The content
  formatting toolbar (bold/italic/strike/list/link/quote) renders as static non-functional icons over a
  plain multiline text field — real rich-text editing is out of scope.
- Q: The second required field (Figma "award", View Kudo labelled it "Danh hiệu (tiêu đề)")? → A: A required
  Danh hiệu title selector — dropdown picking the Kudo's award title from a mock list.
- Q: "Gửi đi" behavior (no backend)? → A: Validate required fields (recipient, danh hiệu, ≥1 hashtag,
  nickname-if-anonymous); on success navigate back to the Kudos screen. Nothing persisted (mock). Huỷ → back.
- Q (decided): Entry point = the Kudos "Hôm nay, bạn muốn gửi kudos đến ai?" send bar (onSendKudos, currently no-op).
- Q (decided): Glyphs ✕ / ✓ / + used for chip-remove / checkmark / add (no new vector assets); chevrons reuse ic_arrow_down.
- Q (decided): Mock data in KudosRepository — recipients (incl. "Dương Huỳnh Xuân Nhật/Nhân" CECV1),
  danh hiệu titleOptions, and the design's literal hashtag option list. Image tiles are gray placeholders.
