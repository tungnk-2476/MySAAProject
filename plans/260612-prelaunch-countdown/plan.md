# Home Hero Countdown LED Redesign — Implementation Plan

**Status:** COMPLETE

**Goal:** Reskin the existing Home hero countdown timer UI from plain text digits to LED 7-segment display,
with event date moved to a live demo date (15 December 2026). No new screen; no navigation gate.
The countdown lives inline in the existing Home hero section.

**Corrected Scope:**
This task originally assumed a full-screen prelaunch gate page with its own navigation logic.
User corrected: the requirement was to **fix the existing Home hero countdown in place** only.
The prelaunch screen / navigation gate / `PrelaunchScreen` / `HomeRoute` gate were **reverted completely**.

**Actual Implementation:**
- NEW `ui/home/SevenSegmentDigit.kt` — Canvas 7-segment LED digit renderer with pure segment map.
- MOD `ui/home/CountdownTimer.kt` — existing hero countdown now renders LED digits inside glassy boxes.
- MOD `ui/home/HomeViewModel.kt` — `eventEpochMillis` repointed to 15/12/2026 (mock demo date).
- MOD `res/values/strings.xml` + `values-vi/strings.xml` — `home_event_date` synced to 15/12/2026.
- NEW test: `SevenSegmentTest.kt` — segment map validation for digits 0–9.

All prelaunch artifacts (PrelaunchScreen, prelaunch nav gate, prelaunchEpochMillis field, prelaunch_title strings) removed.

## Success criteria
- Home hero countdown renders live LED digits with a demoable near-future date.
- No new screen, no navigation changes.
- `assembleDebug` compiles; unit tests pass; on-device visual validated.
- Revert confirmed complete (zero prelaunch symbols in production code).
