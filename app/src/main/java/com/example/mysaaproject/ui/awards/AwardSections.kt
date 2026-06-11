package com.example.mysaaproject.ui.awards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.awards.Award
import com.example.mysaaproject.ui.components.SectionHeader
import com.example.mysaaproject.ui.kudos.FilterDropdown
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/** Highlight block: eyebrow + system title + the award-category single-select dropdown. */
@Composable
fun AwardHighlightBlock(awards: List<Award>, selected: Award, onSelect: (Award) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(eyebrow = appString(R.string.kudos_eyebrow), title = appString(R.string.award_system_title))
        FilterDropdown(
            defaultLabel = selected.name,
            options = awards.map { it.name },
            selected = selected.name,
            onSelect = { name -> awards.firstOrNull { it.name == name }?.let(onSelect) },
        )
    }
}

/** Award info block: badge image, title row, description, and the quantity + prize stat rows. */
@Composable
fun AwardInfoBlock(award: Award) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AwardBadge(award.name)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painterResource(R.drawable.ic_award), null, tint = SaaButton, modifier = Modifier.size(20.dp))
            Text(award.name, color = SaaButton, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        Text(award.longDescription, color = SaaOnDark, fontFamily = Montserrat, fontSize = 14.sp, lineHeight = 21.sp)
        HorizontalDivider(color = SaaOnDark.copy(alpha = 0.12f))
        StatRow(R.drawable.ic_diamond, appString(R.string.award_quantity_label), award.quantity.toString(), award.quantityUnit, valueGold = false)
        HorizontalDivider(color = SaaOnDark.copy(alpha = 0.12f))
        StatRow(R.drawable.ic_award, appString(R.string.award_value_label), award.prizeValue, appString(R.string.award_value_each), valueGold = true)
    }
}

/** Placeholder award badge (no asset): a gold-glow rounded container holding a circular ring + name. */
@Composable
private fun AwardBadge(name: String) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, SaaButton.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier.size(190.dp).clip(CircleShape).border(2.dp, SaaButton, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = name.uppercase(),
                    color = SaaButton,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }
        }
    }
}

@Composable
private fun StatRow(iconRes: Int, label: String, value: String, suffix: String, valueGold: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painterResource(iconRes), null, tint = SaaButton, modifier = Modifier.size(18.dp))
            Text(label, color = SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(value, color = if (valueGold) SaaButton else SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(suffix, color = SaaOnDark.copy(alpha = 0.6f), fontFamily = Montserrat, fontSize = 13.sp, modifier = Modifier.padding(bottom = 3.dp))
        }
    }
}
