package com.example.mysaaproject.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.profile.ProfileBadge
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaContainerDark
import com.example.mysaaproject.ui.theme.SaaOnButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/** Deterministic accent colors for unlocked badges (real badge art isn't exportable from the design). */
private val BADGE_COLORS = listOf(
    Color(0xFF4A9DEC), Color(0xFF8C6CF0), Color(0xFFFFB23E),
    Color(0xFF3DD6C4), Color(0xFFE5536A), Color(0xFF4FC36A),
)

/**
 * "Bộ sưu tập icon của tôi" section: a row of six badge slots and the section label below.
 * Unlocked badges (other-profile) show colored art + title; locked ones (own-profile) are empty
 * dark circles. Tapping a badge is a no-op TODO (icon detail behavior is unspecified in the design).
 */
@Composable
fun IconCollectionRow(
    label: String,
    badges: List<ProfileBadge>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            badges.forEachIndexed { index, badge ->
                BadgeSlot(badge, index, modifier = Modifier.weight(1f))
            }
        }
        Text(
            text = label,
            color = SaaOnDark.copy(alpha = 0.85f),
            fontFamily = Montserrat,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
private fun BadgeSlot(badge: ProfileBadge, index: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clickable { /* TODO: icon detail/tooltip — behavior unspecified */ },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val circle = Modifier.size(44.dp).clip(CircleShape)
        if (badge.unlocked) {
            Box(
                modifier = circle.background(BADGE_COLORS[index % BADGE_COLORS.size]),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_badge),
                    contentDescription = badge.name,
                    tint = SaaOnButton,
                    modifier = Modifier.size(22.dp),
                )
            }
            if (badge.name.isNotEmpty()) {
                Text(
                    text = badge.name,
                    color = SaaOnDark,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 8.sp,
                    lineHeight = 10.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        } else {
            Box(modifier = circle.background(SaaContainerDark).border(1.dp, SaaBorderMuted.copy(alpha = 0.5f), CircleShape))
        }
    }
}
