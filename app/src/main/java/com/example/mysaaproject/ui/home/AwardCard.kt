package com.example.mysaaproject.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.awards.Award
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaCardSurface
import com.example.mysaaproject.ui.theme.SaaOnDark

/**
 * Single award category card (160dp wide): thumbnail placeholder, name, truncated description,
 * and a "Chi tiết" link. The decorative award artwork is not exportable from the design, so a
 * styled placeholder (dark surface + gold ring + name) stands in for it.
 */
@Composable
fun AwardCard(
    award: Award,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .clickable(onClick = onDetailsClick),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SaaCardSurface)
                .border(1.dp, SaaButton.copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = appString(award.name).uppercase(),
                color = SaaButton,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp),
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = appString(award.name),
                color = SaaOnDark,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
            Text(
                text = appString(award.description),
                color = SaaOnDark.copy(alpha = 0.8f),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = appString(R.string.home_award_details),
                color = SaaOnDark,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_outward),
                contentDescription = null,
                tint = SaaOnDark,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}
