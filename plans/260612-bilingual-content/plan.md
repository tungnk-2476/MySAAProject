# Full Bilingual Mock Content (VN/EN) — Implementation Plan

**Status:** COMPLETE

**Context:** After language persistence shipped, the user reported screens (e.g. Awards) whose
content stayed Vietnamese when switching to EN. Root cause: user-facing **mock content lived as
hardcoded Vietnamese strings in the data layer**, and the Send Kudo screen used `stringResource()`
(which reads the Activity locale, not the app's `LocalLocalizedContext`) so it never switched.

**Goal (user choice: "Everything"):** Make all displayed mock prose switch with the in-app language.
Localizable prose is moved to string resources and resolved via `appString` at render. Proper nouns
that are identical across languages (person names, team codes, hashtags, dates) stay as plain strings.

## Changes
- **Awards:** `Award` name/description/longDescription/quantityUnit/prizeValue → `@param:StringRes Int`;
  `AwardsRepository` references `R.string.*`; `AwardCard`, `AwardSections` resolve via `appString`
  (dropdown matches by index). Guarded `appString(0)` for the optional unit/prize defaults.
- **Notifications:** `NotificationItem` message/relativeTime → `@param:StringRes Int`; repo seed +
  `NotificationRow` + preview use res ids / `appString`.
- **Kudos:** `Kudo` title/content + `GiftRecipient.message` → `@param:StringRes Int`; `KudosRepository`
  (sample kudo, recipients, `titleOptions: List<Int>`) references `R.string.*`; `KudoCard`,
  `GiftRecipientsList` resolve via `appString`.
- **Send Kudo:** converted ALL `stringResource()` → `appString()` (Sections + Pickers); `TitleSelector`
  + `SendKudoViewModel`/`SendKudoScreen` switched the title to a `@StringRes Int`.
- **Strings:** ~35 new keys in `values` + `values-vi` (full parity).

## Out of scope / notes
- Names, team codes, hashtags, dates stay String (language-neutral).
- H2 (Awards dropdown matches by resolved label) left as-is — safe for the current distinct names;
  switch to id-based if real data can collide.

## Success criteria
- ✓ Awards, Kudos, Notifications, Send Kudo all switch VN↔EN live.
- ✓ `assembleDebug` compiles; unit tests pass; on-device validated (Awards EN↔VN, Kudos VN, Notifications VN).
- ✓ Reviewer: no `stringResource` left in ui/; en/vi parity (only `app_name` differs, intentional).
