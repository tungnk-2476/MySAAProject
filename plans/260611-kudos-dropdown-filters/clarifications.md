# Clarifications — Kudos Dropdown Filters

MoMorph refs:
- Dropdown phòng ban: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/76k69LQPfj
- Dropdown hashtag: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/V5GRjAdJyb

## Session 2026-06-11

- Q: When a user picks a dropdown item, what happens to the Highlight Kudos carousel? → A: Actually filter the carousel against mock data; chip reflects the selection.
- Q: Single or multiple selection per dropdown? → A: Single-select (picking a new value replaces the old).
- Q: Where do the dropdown list values come from? → A: Literal design values, deduped, stored as mock in KudosRepository.
- Q: Dropdown values that must match kudo data for filtering to work? → A: Hashtags use #Dedicated / #Inspiring (design typo "#Inspring" → "#Inspiring" to match existing kudo hashtags). Departments = CEVC2, CEVC3, CEVC4, CEVC1, OPD, Infra; a `department` field is added to Kudo and distributed across mock highlight kudos.
- Q: Dropdown UI mechanism? → A: Anchored dark popup (Compose DropdownMenu) below the chip, matching the design (decided from design — not a bottom sheet).
