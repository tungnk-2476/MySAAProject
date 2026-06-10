package com.example.mysaaproject.data.notifications

/**
 * A single notification shown on the Notifications screen. [message] and [relativeTime] are mock
 * content from the MoMorph design. [type] selects the icon + tint in the UI layer (kept pure here).
 */
data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val message: String,
    val relativeTime: String,
    val isRead: Boolean = false,
)

/** The seven notification categories defined in the design (each maps to an icon + color in the UI). */
enum class NotificationType {
    KUDOS_RECEIVED,
    HEART_RECEIVED,
    SECRET_BOX,
    LEVEL_UP,
    CONTENT_HIDDEN,
    BADGE_COLLECTED,
    REVIEW_REQUEST,
}
