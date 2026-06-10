# Home Dashboard Implementation & Login Polish

**Date**: 2026-06-10
**Severity**: Medium
**Component**: UI (Login screen refinement + Home/Dashboard screen)
**Status**: Resolved

## What Happened

Two pieces of work in a single session: fixed three visual defects in the Login screen against MoMorph "[iOS] Login" design, then implemented the complete Home dashboard screen from "[iOS] Home" (screenId OuH1BUTYT0). Home includes countdown timer, awards list with stateful loading/empty/error handling, a Kudos feature-flagged section, and FAB with double-tap guards. All code compiles, unit tests pass, emulator-verified.

## The Brutal Truth

This session revealed a persistent pattern: design-to-code fidelity requires obsessive cross-checking of Figma specs, and accepting that not every tool assumption holds. The login fixes were minor but tedious — redistributing padding, swapping image fill modes, pruning an entire localization because the design has no JA. The reviewer flagged a modifier ordering claim that was factually wrong about Compose layout semantics; rather than defer to "authority," I verified and declined, which felt necessary but added friction. Decorative artwork (award orbs, Kudos banner) couldn't be exported from MoMorph due to API errors, forcing styled placeholders instead. The frustrating part is that these small blockers pile up, turning what should be straightforward implementation into a cascade of micro-decisions.

## Technical Details

**Login fixes:**
- Button gap issue: `Box` with `weight(1f)` at bottom jammed the button to the footer. Fixed by splitting vertical space 2:1 between top and bottom spacers.
- Background: `ContentScale.Crop` was centering and clipping the 375×723 asset into a 375×812 frame. Changed to `ContentScale.FillBounds` to match non-uniform fill in design.
- Language enum: "[iOS] Language dropdown" design shows only VN + EN. Removed JA: deleted enum value, test assertion, `values-ja/strings.xml`, and `ic_flag_jp.xml`.

**Home dashboard:**
- ViewModel→Route→Screen architecture mirrors Login feature pattern.
- 14 UI composables: `HomeScreen`, `AwardsSection`, `AwardCard`, `CountdownCard`, `KudosSection` (feature-flagged), `FAB`, etc.
- Countdown: Calendar-based (API-24 safe), configurable target date (2025-12-26), shows "00:00:00" and hides "Coming soon" for past dates.
- Awards state machine: Loading → Success (list) | Empty (no data) | Error + Retry button.
- FAB: double-tap guard per icon (elapsed-time check, not atomic toggle).
- Off-screen navigation: placeholder no-op callbacks (only `/home` in scope this session).
- Test coverage: `HomeCountdownTest` includes past-date edge case.

**Material icons gap:**
- Assumed `Icons.Filled.*` would be available. It's not — no material-icons-core/extended in the Compose BOM. Hand-authored 8 vector drawables instead of adding a dependency.

**Reviewer pushback:**
- Flagged `.navigationBarsPadding().height(72.dp)` as "Major," claiming it collapses the bar to 24dp. Suggested `.height(72.dp).navigationBarsPadding()` instead.
- This is backwards. Padding-before-size adds padding to the content box (result: 72dp + insets). Size-before-padding shrinks content (result: content fits in 72dp minus insets, typically ~24dp visible).
- Verified against Compose docs and tested on emulator. Declined the change.

**Build & test:**
- Compiled cleanly. Unit tests pass. Ran on Android Studio emulator (login → home → scrolled awards list → verified countdown rendering).

## What We Tried

1. **Padding redistribution** (login button): tried simple `Spacer(weight(1f))` at bottom → didn't work. Added dual-spacer layout → worked.
2. **Background fill modes** (login): `Crop` → visible centering/clipping → `FillBounds` → matches design.
3. **Material icons**: checked gradle deps → not available → hand-authored vectors.
4. **Image export from MoMorph**: hit 500/401 errors → pivoted to styled placeholders + flagged.
5. **Modifier ordering claim**: tested on emulator, checked Compose documentation → verified current order is correct → held the line.

## Root Cause Analysis

The fundamental issues weren't technical failures but **assumption gaps**:

1. **Icon library not in BOM**: I assumed a standard Material Compose project includes icons. It doesn't without explicit dependency. Lesson: verify availability before designing.
2. **Visual fill semantics unclear**: Background fill mode requires reading both the design asset dimensions and the frame geometry. Missed that on first pass.
3. **Localization scope creep**: Japanese was added early without checking if the design actually needs it. Answer: it doesn't.
4. **Reviewer authority over verification**: The modifier ordering claim sounded plausible and cited layout theory. The instinct to push back and verify was correct; the frustration was justified. Don't defer to claims about semantics without checking.
5. **Asset export fragility**: MoMorph image endpoints failing means relying on exports is risky. Decorative assets should always have a fallback.

## Lessons Learned

- **Material icons are not free**: Check the BOM; if they're not there, hand-author or add the dependency consciously.
- **Fill and fit matter**: Image scaling modes aren't cosmetic. `Crop` vs `FillBounds` changes what the user sees. Cross-reference pixel dimensions in Figma.
- **Localization scope is a design decision**: Don't add languages just because they might be useful. Let the design (MoMorph) be authoritative.
- **Verify reviewer claims on semantics**: "This will collapse your layout" requires proof, not deference. Compose modifier order is testable; test it.
- **Feature-flagging early pays off**: Kudos section was feature-flagged from day one. When assets failed to export, it could stay in code without blocking the feature. Good call.
- **Emulator verification is non-negotiable**: All layout and timing logic must be verified on device/emulator, not just code inspection. "It compiles" is not "it works."

## Next Steps

1. **Address reviewer concerns formally**: Document the modifier ordering decision in code comments with a link to Compose layout docs. Remove ambiguity from the codebase.
2. **Track missing assets**: Create a follow-up task to retrieve award orbs and Kudos banner images once MoMorph export stabilizes (or source from design files directly).
3. **Audit icon dependencies**: Before the next feature, verify which Material icons are available and plan for hand-authored drawables if gaps exist.
4. **Test off-screen navigation**: When other screens land in scope, wire up the placeholder callbacks properly. For now, they're documented as TODO.

