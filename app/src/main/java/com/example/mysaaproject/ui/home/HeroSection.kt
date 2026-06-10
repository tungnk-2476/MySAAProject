package com.example.mysaaproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/**
 * Hero block: ROOT FURTHER theme logo, the live countdown, event date/venue/livestream,
 * and the ABOUT AWARD / ABOUT KUDOS call-to-action buttons.
 */
@Composable
fun HeroSection(
    countdown: Countdown,
    onAboutAward: () -> Unit,
    onAboutKudos: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(32.dp)) {
        Image(
            painter = painterResource(R.drawable.logo_root_further),
            contentDescription = "ROOT FURTHER",
            modifier = Modifier
                .width(247.dp)
                .height(109.dp),
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CountdownTimer(countdown)
            EventInfo()
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PillButton(label = appString(R.string.home_about_award), onClick = onAboutAward)
            PillButton(label = appString(R.string.home_about_kudos), onClick = onAboutKudos)
        }
    }
}

@Composable
private fun EventInfo() {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        InfoLine(appString(R.string.home_event_time_label), appString(R.string.home_event_date))
        InfoLine(appString(R.string.home_event_venue_label), appString(R.string.home_event_venue))
        Text(
            text = appString(R.string.home_event_livestream),
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        )
    }
}

@Composable
private fun InfoLine(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
        )
        Text(
            text = value,
            color = SaaButton,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }
}
