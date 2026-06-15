package com.example.mysaaproject.data.notifications

import androidx.annotation.StringRes

/**
 * A single notification shown on the Notifications screen. [message] and [relativeTime] are held as
 * string-resource ids (mock content from the MoMorph design, resolved via `appString` at render so
 * they switch with the app language). [type] selects the icon + tint in the UI layer (kept pure here).
 */
data class NotificationItem(
    val id: String,
    val type: NotificationType,
    @param:StringRes val message: Int,
    @param:StringRes val relativeTime: Int,
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
