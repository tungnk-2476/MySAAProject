package com.example.mysaaproject.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/**
 * Countdown block: "Coming soon" (while upcoming) over three DAYS / HOURS / MINUTES units,
 * each rendered as two glassy digit boxes (32×56dp, 8dp radius, 0.5dp gold border, white
 * gradient) per the MoMorph design.
 */
@Composable
fun CountdownTimer(countdown: Countdown, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (countdown.isBeforeEvent) {
            Text(
                text = appString(R.string.home_coming_soon),
                color = SaaOnDark,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            CountdownUnit(countdown.days, appString(R.string.home_countdown_days))
            CountdownUnit(countdown.hours, appString(R.string.home_countdown_hours))
            CountdownUnit(countdown.minutes, appString(R.string.home_countdown_minutes))
        }
    }
}

@Composable
private fun CountdownUnit(value: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            "%02d".format(value).forEach { digit -> DigitBox(digit.digitToInt()) }
        }
        Text(
            text = label,
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
        )
    }
}

@Composable
private fun DigitBox(digit: Int) {
    Box(modifier = Modifier.size(width = 32.dp, height = 56.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0.5f), Color.White.copy(alpha = 0.05f)),
                    ),
                )
                .border(0.5.dp, SaaButton.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
        )
        // LED 7-segment digit, matching the MoMorph countdown design.
        SevenSegmentDigit(
            digit = digit,
            litColor = SaaOnDark,
            modifier = Modifier.size(width = 18.dp, height = 34.dp),
        )
    }
}
