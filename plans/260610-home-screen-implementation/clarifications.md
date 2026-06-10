# Clarifications

## Session 2026-06-10
- Q: How to handle off-screen nav targets (Awards/Kudos/Search/Notifications/Profile/WriteKudo/AwardDetail)? → A: Hoisted no-op callbacks + TODO (only /home in scope)
- Q: Countdown behavior given event date 26/12/2025 is in the past? → A: Real timer to a configurable date (default 2025-12-26); when elapsed hide "Coming soon" and show 00/00/00 per TC_FUN_002
- Q: Awards data source given no backend? → A: Stub repository exposing Loading/Success/Empty/Error + Retry; mock data from design; default Success
- Q: Faithfulness of secondary behaviors (badge, Kudos flag, FAB double-tap)? → A: Wire as simple state flags — isKudosAvailable=true, notification unread>0, FAB double-tap guarded
