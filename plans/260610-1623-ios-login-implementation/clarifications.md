## Session 2026-06-10
- Q: How should the "LOGIN With Google" button behave on this bare project (no backend/auth)? → A: Stubbed auth flow — loading state + double-click guard, simulate auth, then navigate to placeholder Home; real Google OAuth deferred
- Q: How much VN/EN/JA localization to wire for the language switcher? → A: Full VN/EN/JA i18n — switcher re-renders description + copyright via string resources without activity recreation
- Q: Include Home navigation + session persistence scaffolding? → A: Yes — placeholder Home + DataStore session persistence (auto-login, redirect-if-authenticated, logout-relaunch)
