package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.data.kudos.GiftRecipient
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaContainerDark
import com.example.mysaaproject.ui.theme.SaaOnDark

/** "Latest 10 gift recipients" list (D.3). Avatar/name taps are no-op TODO (profile out of scope). */
@Composable
fun GiftRecipientsList(
    title: String,
    recipients: List<GiftRecipient>,
    onRecipientClick: (GiftRecipient) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SaaContainerDark)
            .border(1.dp, SaaBorderMuted, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        recipients.forEach { recipient ->
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onRecipientClick(recipient) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Avatar(recipient.name, size = 40.dp)
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(recipient.name, color = SaaButton, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(appString(recipient.message), color = SaaOnDark, fontFamily = Montserrat, fontSize = 12.sp)
                }
            }
        }
    }
}
