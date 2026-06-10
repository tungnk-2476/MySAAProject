package com.example.mysaaproject.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaInactiveTab
import com.example.mysaaproject.ui.theme.SaaNavSurface

/**
 * Fixed bottom navigation: SAA 2025 (active, gold) · Awards · Kudos · Profile. The active tab
 * is highlighted gold; the rest are reduced-opacity white (TC_IOS_HOME_FUN_018).
 */
@Composable
fun HomeBottomBar(
    onAwards: () -> Unit,
    onKudos: () -> Unit,
    onProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SaaNavSurface)
            .navigationBarsPadding()
            .height(72.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NavTab(
            icon = painterResource(R.drawable.ic_home),
            label = appString(R.string.home_nav_saa),
            active = true,
            onClick = {}, // already on Home (TC_IOS_HOME_ACC_006)
        )
        NavTab(
            icon = painterResource(R.drawable.ic_trophy),
            label = appString(R.string.home_nav_awards),
            active = false,
            onClick = onAwards,
        )
        NavTab(
            icon = painterResource(R.drawable.ic_heart),
            label = appString(R.string.home_nav_kudos),
            active = false,
            onClick = onKudos,
        )
        NavTab(
            icon = painterResource(R.drawable.ic_person),
            label = appString(R.string.home_nav_profile),
            active = false,
            onClick = onProfile,
        )
    }
}

@Composable
private fun RowScope.NavTab(
    icon: Painter,
    label: String,
    active: Boolean,
    onClick: () -> Unit,
) {
    val tint = if (active) SaaButton else SaaInactiveTab
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(painter = icon, contentDescription = label, tint = tint, modifier = Modifier.size(24.dp))
        Text(
            text = label,
            color = tint,
            fontFamily = Montserrat,
            fontWeight = if (active) FontWeight.Medium else FontWeight.Normal,
            fontSize = 11.sp,
        )
    }
}
