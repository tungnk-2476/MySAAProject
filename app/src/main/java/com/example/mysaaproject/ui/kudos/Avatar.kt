package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.ui.theme.Montserrat

private val AVATAR_COLORS = listOf(
    Color(0xFF4A9DEC), Color(0xFFFF7AA8), Color(0xFF4FC36A),
    Color(0xFFB07CFF), Color(0xFFFFA94A), Color(0xFF3DD6C4),
)

/**
 * Placeholder avatar: a colored circle with the name's initial. Real avatar images aren't
 * exportable from the design, so this stands in deterministically (color derived from the name).
 */
@Composable
fun Avatar(name: String, modifier: Modifier = Modifier, size: Dp = 32.dp) {
    val initial = name.trim().firstOrNull()?.uppercase() ?: "?"
    val color = AVATAR_COLORS[(name.hashCode() and 0x7fffffff) % AVATAR_COLORS.size]
    Box(
        modifier = modifier.size(size).clip(CircleShape).background(color),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initial,
            color = Color.White,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = (size.value * 0.42f).sp,
        )
    }
}
