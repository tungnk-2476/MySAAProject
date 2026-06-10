package com.example.mysaaproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.login.LanguageSelector
import com.example.mysaaproject.ui.theme.SaaBadge
import com.example.mysaaproject.ui.theme.SaaOnDark

/**
 * Fixed Home header: Sun* Awards logo (left), then language switcher, search, and a
 * notification bell with an unread badge dot (TC_IOS_HOME_GUI_006).
 */
@Composable
fun HomeHeader(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    unreadCount: Int,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.logo_sun_award),
            contentDescription = "Sun* Annual Awards",
            modifier = Modifier
                .width(48.dp)
                .height(44.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LanguageSelector(selected = language, onSelect = onLanguageSelected)
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = appString(R.string.home_cd_search),
                tint = SaaOnDark,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onSearchClick),
            )
            Box {
                Icon(
                    painter = painterResource(R.drawable.ic_bell),
                    contentDescription = appString(R.string.home_cd_notifications),
                    tint = SaaOnDark,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onNotificationsClick),
                )
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 2.dp, y = (-2).dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(SaaBadge),
                    )
                }
            }
        }
    }
}
