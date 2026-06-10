package com.example.mysaaproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaGoldGlow
import com.example.mysaaproject.ui.theme.SaaOnButton

private const val DOUBLE_TAP_GUARD_MS = 600L

/**
 * Floating action button: a gold pill with a pencil (write Kudos) and the Sun* mark (Kudos feed),
 * split by a divider. Rapid taps are debounced so navigation fires once (TC_IOS_HOME_FUN_013).
 */
@Composable
fun HomeFab(
    onWriteKudos: () -> Unit,
    onKudosShortcut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Per-action debounce so a tap on one icon doesn't silence the other (TC_IOS_HOME_FUN_013).
    var lastWriteAt by remember { mutableLongStateOf(0L) }
    var lastShortcutAt by remember { mutableLongStateOf(0L) }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(SaaButton)
            .border(1.dp, SaaGoldGlow, RoundedCornerShape(24.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_pencil),
            contentDescription = appString(R.string.home_cd_write_kudos),
            tint = SaaOnButton,
            modifier = Modifier
                .size(22.dp)
                .clickable {
                    val now = System.currentTimeMillis()
                    if (now - lastWriteAt >= DOUBLE_TAP_GUARD_MS) {
                        lastWriteAt = now
                        onWriteKudos()
                    }
                },
        )
        Text(
            text = "/",
            color = SaaOnButton,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp,
        )
        Image(
            painter = painterResource(R.drawable.logo_sun_award),
            contentDescription = appString(R.string.home_cd_kudos_shortcut),
            modifier = Modifier
                .size(22.dp)
                .clickable {
                    val now = System.currentTimeMillis()
                    if (now - lastShortcutAt >= DOUBLE_TAP_GUARD_MS) {
                        lastShortcutAt = now
                        onKudosShortcut()
                    }
                },
        )
    }
}
