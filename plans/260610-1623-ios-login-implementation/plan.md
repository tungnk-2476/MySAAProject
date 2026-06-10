# [iOS] Login — Android Compose Implementation

MoMorph: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/8HGlvYGJWq
Target: Android Jetpack Compose (package `com.example.mysaaproject`). Decisions: [clarifications.md](clarifications.md)

## Design facts (authoritative, from MoMorph)
- Frame 375×812, bg `#00101A`. Keyvisual artwork background.
- Logo top-left (48×44, x20 y52). Language selector top-right (flag + "VN" + ▼, x~301 y70).
- ROOT FURTHER image (247×109, x20 y252). Description (Montserrat Light 14/20, x20 y393, white).
- Button "LOGIN With Google" (246×40, x65 y626, bg `#FFEA9E`, radius 4, label Montserrat Medium 14 `#00101A`, Google icon right).
- Copyright (Montserrat Regular 12, centered, y780, white).

## Assets (recovered from git dangling blobs — MCP media download 401'd)
logo_sun_award.png, logo_root_further.png, ic_google.png, keyvisual_bg.png (nodpi),
montserrat_light/regular/medium.ttf. Flags (VN/GB/JP) + down chevron = hand-authored vector drawables.

## Tracks
**Track A — UI (presentational)**
- A1 Theme: SaaColors, Montserrat FontFamily in Type.kt, dark login palette.
- A2 Composables: LoginScreen (stateless), LanguageSelector, GoogleLoginButton. State hoisted.

**Track B — Behavior/backend**
- B1 Localization: values(vi default)/values-en/values-ja string resources + LocaleProvider CompositionLocal for live in-screen switching.
- B2 Session: DataStore SessionRepository (logged-in flag/token).
- B3 Auth+ViewModel: stubbed AuthRepository (simulated delay→success), LoginViewModel (loading/error, double-click guard).
- B4 Nav+Home: AppNavHost (login↔home), placeholder HomeScreen with logout, startDestination from session (auto-login / redirect).

**Integration**: wire LoginScreen→LoginViewModel + nav + LocaleProvider; deps in build.gradle; MainActivity hosts NavHost; build + emulator screenshot vs Figma.

## Status — COMPLETE (2026-06-10)
- [x] A1 Theme  - [x] A2 UI  - [x] B1 i18n  - [x] B2 Session  - [x] B3 Auth/VM  - [x] B4 Nav/Home  - [x] Integration  - [x] Test+Review

Verified on Pixel_7 emulator: layout matches Figma; VN→EN live re-render; login→loading→Home;
auto-login on relaunch; logout→Login. Build + unit tests green. Reviewer: DONE_WITH_CONCERNS
(addressed: backup exclusion for session token, error-state wired, navController hoisted,
dynamicColor off). Deferred (tied to real OAuth, out of scope): SharedFlow nav events,
EncryptedDataStore, real Google Credential Manager.
