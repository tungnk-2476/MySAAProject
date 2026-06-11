package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/**
 * Spotlight Board (B.6/B.7) — static placeholder: a non-interactive search bar over a dark board
 * showing the total Kudos count with scattered Sunner names. Pan/zoom/live-search is out of scope.
 */
@Composable
fun SpotlightBoard(count: Int, names: List<String>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.radialGradient(listOf(Color(0xFF14202B), Color(0xFF050A0F))),
            ),
    ) {
        // Scattered names hint at the network graph.
        Text(names.getOrElse(0) { "" }, color = SaaOnDark.copy(alpha = 0.35f), fontFamily = Montserrat, fontSize = 11.sp, modifier = Modifier.align(Alignment.TopStart).padding(20.dp))
        Text(names.getOrElse(1) { "" }, color = SaaOnDark.copy(alpha = 0.3f), fontFamily = Montserrat, fontSize = 11.sp, modifier = Modifier.align(Alignment.TopEnd).padding(24.dp))
        Text(names.getOrElse(2) { "" }, color = SaaOnDark.copy(alpha = 0.3f), fontFamily = Montserrat, fontSize = 11.sp, modifier = Modifier.align(Alignment.CenterStart).padding(16.dp))
        Text(names.getOrElse(3) { "" }, color = SaaOnDark.copy(alpha = 0.3f), fontFamily = Montserrat, fontSize = 11.sp, modifier = Modifier.align(Alignment.BottomStart).padding(28.dp))
        Text(names.getOrElse(4) { "" }, color = SaaOnDark.copy(alpha = 0.3f), fontFamily = Montserrat, fontSize = 11.sp, modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp))

        Text(
            text = "$count KUDOS",
            color = SaaButton,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center),
        )

        // Non-interactive search bar (display-only).
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(12.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.12f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(painter = painterResource(R.drawable.ic_search), contentDescription = null, tint = SaaOnDark.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
            Text(appString(R.string.kudos_search_placeholder), color = SaaOnDark.copy(alpha = 0.6f), fontFamily = Montserrat, fontSize = 13.sp)
        }
    }
}
