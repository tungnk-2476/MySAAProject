# Phase 01 — Notifications Screen + Shared Unread State + Nav

## Overview
Priority: High · Status: ✅ Complete
Presentational screen + ViewModel + Route (mirrors Home). Read-state lives in a shared singleton
repository so the Home bell badge reacts. All visual values from MoMorph node specs.

## Design facts (authoritative — frame 375w)
- **Top nav** (`_TopNavigation-content`, 375×42): back icon (left, x~20) + centered title "Notifications" (vi: "Thông báo").
- **Background**: keyvisual BG faded behind a dark scrim (legibility), like Home.
- **Mark-all-read** button (`mms_Button_read all`, 181×40, x20): list icon + "Đánh dấu đọc tất cả" (gap 4dp).
- **Notification list** (`Notification list`): rows, full-width (x20–355), padding 8dp, gap 16dp (icon↔content), `border-bottom 1px #2E3940`.
  - **Icon** (24dp) — per type, tinted to the spec's named color.
  - **Content**: message text (14sp, lineHeight 20; **unread = bold/700**, read = normal) + relative time (12–14sp, muted). Type 5 adds inline "Tiêu chuẩn cộng đồng ↗" link.
  - **Unread dot**: 8×8 circle `#D4271D`, right side; shown only when unread.
- **7 types** (icon · named color):
  1. KUDOS_RECEIVED — envelope · blue
  2. HEART_RECEIVED — heart · pink
  3. SECRET_BOX — gift · green
  4. LEVEL_UP — star · yellow
  5. CONTENT_HIDDEN — warning triangle · yellow (+ inline community-standards link)
  6. BADGE_COLLECTED — badge/shield · blue
  7. REVIEW_REQUEST — pen · purple

## Files to create
**data/notifications/**
- `NotificationItem.kt` — `data class NotificationItem(id, type: NotificationType, message, relativeTime, isRead)`; `enum NotificationType { KUDOS_RECEIVED, HEART_RECEIVED, SECRET_BOX, LEVEL_UP, CONTENT_HIDDEN, BADGE_COLLECTED, REVIEW_REQUEST }` (pure — no UI types).
- `NotificationsRepository.kt` — `object` singleton: `MutableStateFlow<List<NotificationItem>>` seeded with 7 design mock items; `items: StateFlow`; `markRead(id)`, `markAllRead()`.

**ui/notifications/**
- `NotificationsViewModel.kt` — exposes `items` (StateFlow); `markRead(id)`, `markAllRead()`.
- `NotificationsRoute.kt` — VM + `onBack` + per-type no-op TODO nav → screen.
- `NotificationsScreen.kt` — top bar + mark-all button + `LazyColumn` of rows over keyvisual+scrim. Preview.
- `NotificationRow.kt` — icon + content (+ inline link for CONTENT_HIDDEN) + unread dot.
- `NotificationTypeUi.kt` — maps `NotificationType` → icon `@DrawableRes` + tint `Color` (UI layer, keeps data pure).

## Files to modify
- `HomeViewModel.kt` — replace static `unreadNotifications` with `unreadCount: StateFlow<Int>` derived from `NotificationsRepository.items`.
- `HomeRoute.kt` — collect `unreadCount`; add `onOpenNotifications` param; wire bell to it (was no-op TODO).
- `AppNavHost.kt` — add `Routes.NOTIFICATIONS`; `composable` → `NotificationsRoute(onBack = popBackStack, …)`; pass `onOpenNotifications = navigate(NOTIFICATIONS)` to `HomeRoute`.
- `strings.xml` (+ values-vi) — notif title, mark-all-read, community-standards link label, back content-desc.
- `Color.kt` — add `SaaDivider` (#2E3940); type tint colors live in `NotificationTypeUi`.
- New vectors: `ic_mail`, `ic_gift`, `ic_star`, `ic_warning`, `ic_badge`, `ic_arrow_back`, `ic_list`.

## Todo
- [x] strings + SaaDivider/SaaUnreadDot colors + 7 vector drawables
- [x] NotificationItem + NotificationType + NotificationsRepository (7 mock items, n1 unread)
- [x] NotificationsViewModel (items, markRead, markAllRead)
- [x] NotificationTypeUi mapper
- [x] NotificationRow + NotificationsScreen + NotificationsRoute
- [x] Wire Home: unreadCount flow + bell navigation (HomeViewModel, HomeRoute, AppNavHost)
- [x] Unit test: NotificationsRepositoryTest (markRead decrements, markAllRead clears, no-op unknown id)
- [x] Compile, test, emulator-verify (Home badge clears after mark-all)
- [x] Reviewer 8/10; applied H1 (reset on logout), M1 (launchSingleTop), M2 (preview), N1 (IconButton), N3 (guard markRead)

## Success criteria
- Bell on Home navigates to Notifications; back returns.
- Tapping an unread item removes its dot and decrements the Home bell badge.
- "Mark all read" clears all dots; Home bell badge disappears.
- Compiles; unit tests pass; emulator-verified.

## Risks
- Shared singleton mutable state across ViewModels → keep it a simple StateFlow; derive unread via map.
- Icon artwork not exportable → hand-authored vectors tinted per spec colors (flagged).
