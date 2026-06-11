# Clarifications — Award Top Talent (Award Detail) Screen

MoMorph refs:
- Award Top Talent: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/c-QM3_zjkG

## Session 2026-06-11

Rich design (18 specs + 34 test cases), clear behavior. Decisions from specs/test cases + conventions:

- Q: Award-category dropdown behavior? → A: A single-select dropdown listing all award categories; selecting
  one swaps the whole info block (badge/title/description/quantity/prize) — per FUN_005–009. Mock `Award`
  list in a repository; default = Top Talent.
- Q: Badge image + Kudos banner image? → A: Placeholders (no photo assets): the TOP TALENT badge = a gold
  ring/box with the award name; the Kudos banner = a dark box with the KUDOS wordmark. (image_url null → placeholder, per FUN_004/GUI_007.)
- Q: Loading / error / retry states (FUN_002/003)? → A: Out of scope — mock data is synchronous, no API.
- Q: Entry point? → A: The bottom-nav "Awards" tab (currently no-op across screens) opens this screen; also
  Home's award "Chi tiết"/About-award. Awards tab active (gold) on this screen.
- Q: "Chi tiết" in the Kudos promo block? → A: Navigates to the Kudos screen (FUN_012).
- Q: Reuse vs new? → A: Reuse HomeHeader, KudosPageBanner, SaaBottomBar, and the FilterDropdown dark
  single-select dropdown for the category selector. Hand-author 2 small vector icons (diamond, award) for the stat rows.
