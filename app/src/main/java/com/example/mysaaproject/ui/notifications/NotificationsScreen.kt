package com.example.mysaaproject.ui.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.notifications.NotificationItem
import com.example.mysaaproject.data.notifications.NotificationType
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaDivider
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp

/**
 * Presentational Notifications screen: top bar (back + title), a "mark all read" action, and the
 * notification list. State is hoisted (driven by [NotificationsRoute]/[NotificationsViewModel]).
 */
@Composable
fun NotificationsScreen(
    items: List<NotificationItem>,
    onBack: () -> Unit,
    onItemClick: (NotificationItem) -> Unit,
    onMarkAllRead: () -> Unit,
    onCommunityStandards: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(modifier = Modifier.fillMaxSize().background(SaaBackground.copy(alpha = 0.82f)))

        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            TopBar(onBack = onBack)
            MarkAllReadAction(onClick = onMarkAllRead)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                contentPadding = PaddingValues(horizontal = CONTENT_INSET),
            ) {
                items(items, key = { it.id }) { item ->
                    NotificationRow(
                        item = item,
                        onClick = { onItemClick(item) },
                        onCommunityStandards = onCommunityStandards,
                    )
                    HorizontalDivider(thickness = 1.dp, color = SaaDivider)
                }
            }
        }
    }
}

@Composable
private fun TopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = appString(R.string.notif_cd_back),
                tint = SaaOnDark,
                modifier = Modifier.size(24.dp),
            )
        }
        Text(
            text = appString(R.string.notif_title),
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
        )
    }
}

@Composable
private fun MarkAllReadAction(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = CONTENT_INSET, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_list),
            contentDescription = null,
            tint = SaaOnDark,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = appString(R.string.notif_mark_all_read),
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun NotificationsScreenPreview() {
    val sample = listOf(
        NotificationItem("p1", NotificationType.KUDOS_RECEIVED, "Sunner vừa gửi đến bạn lời ghi nhận đầy yêu thương!", "15 phút trước", isRead = false),
        NotificationItem("p2", NotificationType.CONTENT_HIDDEN, "Tiếc quá! Một lời nhắn bị tạm ẩn vì vướng một số tiêu chuẩn!", "1 tháng trước", isRead = true),
    )
    ProvideAppLanguage(language = AppLanguage.VN) {
        NotificationsScreen(
            items = sample,
            onBack = {}, onItemClick = {}, onMarkAllRead = {}, onCommunityStandards = {},
        )
    }
}
