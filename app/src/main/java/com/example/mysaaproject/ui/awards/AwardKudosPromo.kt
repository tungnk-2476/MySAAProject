package com.example.mysaaproject.ui.awards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnButton
import com.example.mysaaproject.ui.theme.SaaOnDark
import androidx.compose.ui.text.font.FontWeight

/** Sun* Kudos promo block: label + title + banner + "what's new" badge + description + Chi tiết button. */
@Composable
fun KudosPromoBlock(onDetails: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(appString(R.string.home_kudos_eyebrow), color = SaaOnDark.copy(alpha = 0.7f), fontFamily = Montserrat, fontSize = 12.sp)
        Text(appString(R.string.award_kudos_title), color = SaaButton, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        KudosPromoBanner()
        Text(appString(R.string.award_kudos_new_badge), color = SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(appString(R.string.award_kudos_desc), color = SaaOnDark.copy(alpha = 0.85f), fontFamily = Montserrat, fontSize = 14.sp, lineHeight = 21.sp)
        DetailButton(onDetails)
    }
}

/** Placeholder Kudos banner (no asset): dark gradient with the KUDOS wordmark. */
@Composable
private fun KudosPromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFF0A2231), Color(0xFF1A1206)))),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painterResource(R.drawable.logo_sun_award), null, tint = Color.Unspecified, modifier = Modifier.size(36.dp))
            Text("KUDOS", color = SaaButton, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 28.sp, letterSpacing = 2.sp)
        }
    }
}

@Composable
private fun DetailButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SaaButton)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(appString(R.string.award_detail_btn), color = SaaOnButton, fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Icon(painterResource(R.drawable.ic_arrow_outward), null, tint = SaaOnButton, modifier = Modifier.size(16.dp))
    }
}
