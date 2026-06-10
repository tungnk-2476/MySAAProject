package com.example.mysaaproject.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.notifications.NotificationItem
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaOnDark
import com.example.mysaaproject.ui.theme.SaaUnreadDot

/**
 * One notification row: type icon (tinted) + message and relative time + an unread dot.
 * Unread rows render bold; type 5 (content hidden) adds an inline "community standards" link.
 */
@Composable
fun NotificationRow(
    item: NotificationItem,
    onClick: () -> Unit,
    onCommunityStandards: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ui = item.type.ui()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            painter = painterResource(ui.icon),
            contentDescription = null,
            tint = ui.tint,
            modifier = Modifier.size(24.dp),
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = item.message,
                color = if (item.isRead) SaaOnDark.copy(alpha = 0.75f) else SaaOnDark,
                fontFamily = Montserrat,
                fontWeight = if (item.isRead) FontWeight.Normal else FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            )

            if (item.type.hasCommunityStandardsLink) {
                Row(
                    modifier = Modifier.clickable(onClick = onCommunityStandards),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = appString(R.string.notif_community_standards),
                        color = SaaOnDark,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline,
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_outward),
                        contentDescription = null,
                        tint = SaaOnDark,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }

            Text(
                text = item.relativeTime,
                color = SaaOnDark.copy(alpha = 0.5f),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
        }

        Box(modifier = Modifier.size(8.dp)) {
            if (!item.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(SaaUnreadDot),
                )
            }
        }
    }
}
