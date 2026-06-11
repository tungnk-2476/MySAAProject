package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.KudosStats
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaContainerDark
import com.example.mysaaproject.ui.theme.SaaOnButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/** Personal statistics block (D.1) + "Open Secret Box" CTA. */
@Composable
fun KudosStatsBlock(stats: KudosStats, onOpenSecretBox: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SaaContainerDark)
            .border(1.dp, SaaBorderMuted, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        StatRow(appString(R.string.kudos_stat_received), stats.received)
        StatRow(appString(R.string.kudos_stat_sent), stats.sent)
        StatRow(appString(R.string.kudos_stat_hearts), stats.hearts, fire = stats.fireBonusActive)
        HorizontalDivider(color = SaaBorderMuted.copy(alpha = 0.4f))
        StatRow(appString(R.string.kudos_stat_box_opened), stats.secretBoxOpened)
        StatRow(appString(R.string.kudos_stat_box_unopened), stats.secretBoxUnopened)

        Button(
            onClick = onOpenSecretBox,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SaaButton, contentColor = SaaOnButton),
            modifier = Modifier.fillMaxWidth().height(40.dp),
        ) {
            Text(appString(R.string.kudos_open_secret_box), fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Icon(painter = painterResource(R.drawable.ic_gift), contentDescription = null, tint = SaaOnButton, modifier = Modifier.padding(start = 8.dp).size(18.dp))
        }
    }
}

@Composable
private fun StatRow(label: String, value: Int, fire: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = SaaOnDark, fontFamily = Montserrat, fontSize = 13.sp, modifier = Modifier.weight(1f))
        if (fire) FireBadge()
        Text("$value", color = SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
private fun FireBadge() {
    Text(
        text = "x2",
        color = Color.White,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFFF6A00))
            .padding(horizontal = 8.dp, vertical = 2.dp),
    )
}
