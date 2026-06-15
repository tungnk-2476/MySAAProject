package com.example.mysaaproject.data.notifications

import com.example.mysaaproject.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * In-memory singleton holding the notification list and read-state. Shared by the Notifications
 * screen and the Home bell badge, so marking items read updates both live (TC_NOTIF_FUN_001/002).
 * No backend yet — seeded with the seven mock notifications from the design.
 */
object NotificationsRepository {

    private val _items = MutableStateFlow(seed())
    val items: StateFlow<List<NotificationItem>> = _items.asStateFlow()

    fun markRead(id: String) {
        _items.update { list -> list.map { if (it.id == id) it.copy(isRead = true) else it } }
    }

    fun markAllRead() {
        _items.update { list -> list.map { it.copy(isRead = true) } }
    }

    /** Resets to the initial seed — used by tests to isolate runs against this shared singleton. */
    fun reset() {
        _items.value = seed()
    }

    private fun seed(): List<NotificationItem> = listOf(
        NotificationItem(
            id = "n1",
            type = NotificationType.KUDOS_RECEIVED,
            message = R.string.notif_msg_kudos_received,
            relativeTime = R.string.notif_time_15min,
            isRead = false,
        ),
        NotificationItem(
            id = "n2",
            type = NotificationType.HEART_RECEIVED,
            message = R.string.notif_msg_heart_received,
            relativeTime = R.string.notif_time_1hour,
            isRead = true,
        ),
        NotificationItem(
            id = "n3",
            type = NotificationType.SECRET_BOX,
            message = R.string.notif_msg_secret_box,
            relativeTime = R.string.notif_time_1day,
            isRead = true,
        ),
        NotificationItem(
            id = "n4",
            type = NotificationType.LEVEL_UP,
            message = R.string.notif_msg_level_up,
            relativeTime = R.string.notif_time_1day,
            isRead = true,
        ),
        NotificationItem(
            id = "n5",
            type = NotificationType.CONTENT_HIDDEN,
            message = R.string.notif_msg_content_hidden,
            relativeTime = R.string.notif_time_1month,
            isRead = true,
        ),
        NotificationItem(
            id = "n6",
            type = NotificationType.BADGE_COLLECTED,
            message = R.string.notif_msg_badge_collected,
            relativeTime = R.string.notif_time_1month,
            isRead = true,
        ),
        NotificationItem(
            id = "n7",
            type = NotificationType.REVIEW_REQUEST,
            message = R.string.notif_msg_review_request,
            relativeTime = R.string.notif_time_1month,
            isRead = true,
        ),
    )
}
