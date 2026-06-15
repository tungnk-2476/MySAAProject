# Clarifications — Profile Screen

MoMorph refs:
- [iOS] Profile bản thân (own): https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/hSH7L8doXB
- [iOS] Profile người khác (other): https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/bEpdheM0yU

## Session 2026-06-12
- Q: Target project (PR link was netkeirin-flutter, but CWD is MySAAProject)? → A: MySAAProject (Android Compose)
- Q: Which Profile design(s) to implement? → A: Own profile (hSH7L8doXB) + other-person profile (bEpdheM0yU)
- Q: How is the other-person profile reached? → A: Wire kudo card sender/receiver taps (also recipient/spotlight where trivial)
- Q: Where does the other-profile "Gửi lời cảm ơn..." CTA lead? → A: Open the existing Send Kudo screen
- Q: How to handle actions marked unclear/TBD (avatar tap, icon-badge tap, Secret Box, Copy Link)? → A: No-op TODO, matching sibling screens
