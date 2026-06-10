package com.example.mysaaproject.ui.notifications

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.mysaaproject.R
import com.example.mysaaproject.data.notifications.NotificationType

/** Icon + tint for a notification type. Colors follow the design spec's named colors per type. */
data class NotificationTypeUi(@param:DrawableRes val icon: Int, val tint: Color)

fun NotificationType.ui(): NotificationTypeUi = when (this) {
    NotificationType.KUDOS_RECEIVED -> NotificationTypeUi(R.drawable.ic_mail, Color(0xFF4A9DEC))
    NotificationType.HEART_RECEIVED -> NotificationTypeUi(R.drawable.ic_heart, Color(0xFFFF7AA8))
    NotificationType.SECRET_BOX -> NotificationTypeUi(R.drawable.ic_gift, Color(0xFF4FC36A))
    NotificationType.LEVEL_UP -> NotificationTypeUi(R.drawable.ic_star, Color(0xFFFFD24A))
    NotificationType.CONTENT_HIDDEN -> NotificationTypeUi(R.drawable.ic_warning, Color(0xFFFFD24A))
    NotificationType.BADGE_COLLECTED -> NotificationTypeUi(R.drawable.ic_badge, Color(0xFF4A9DEC))
    NotificationType.REVIEW_REQUEST -> NotificationTypeUi(R.drawable.ic_pencil, Color(0xFFB07CFF))
}

/** Type 5 (content hidden) shows an inline "community standards" link. */
val NotificationType.hasCommunityStandardsLink: Boolean
    get() = this == NotificationType.CONTENT_HIDDEN
