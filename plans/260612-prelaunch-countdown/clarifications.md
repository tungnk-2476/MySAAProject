# Clarifications — Home Hero Countdown LED Redesign

MoMorph refs:
- Home hero countdown context: https://momorph.ai/files/9ypp4enmFmdK3YAFJLIu6C/screens/OuH1BUTYT0

## Session 2026-06-12

**Scope Correction (Mid-Task):**
Initial assumption was a full-screen prelaunch gate page with nav control. User corrected: requirement is to fix the **existing Home hero countdown in place** with LED digit styling. No new screen, no navigation gate.

**Final Decisions:**
- Q: What is the actual scope? → A: Reskin existing Home hero countdown (inline) with LED 7-segment digits; move event date to a future demo date (15/12/2026). No prelaunch gate or new screen.
- Q: How to demonstrate countdown being live? → A: Point `eventEpochMillis` to 15/12/2026 (33 days from session date 12/06/2026); countdown ticks live in hero section.
- Q: Digit visual? → A: LED 7-segment Canvas renderer (custom, zero font dependencies); matches Home hero design mockup; ghost segments for unlitness.
- Decision: Revert all prelaunch page scaffolding (PrelaunchScreen, nav gate, prelaunch_title, prelaunchEpochMillis) — confirmed complete post-implementation.
- Decision: Event date source of truth now 15/12/2026 across both `home_event_date` strings (XML) and `eventEpochMillis` in code.
