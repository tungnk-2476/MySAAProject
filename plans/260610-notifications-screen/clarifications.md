# Clarifications

## Session 2026-06-10
- Q: How to manage notification read-state given the Home bell badge must update (FUN_001/002)? → A: Shared in-memory singleton NotificationsRepository observed by both Home and Notifications
- Q: What should tapping a notification do (per-type detail screens don't exist)? → A: Mark read + no-op TODO per-type navigation
