package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaCardCream
import com.example.mysaaproject.ui.theme.SaaOnButton

private val InkColor = SaaOnButton // dark text on the cream card
private val MutedInk = SaaOnButton.copy(alpha = 0.5f)
private val HashtagColor = Color(0xFFCC4125)
private val HeartRed = Color(0xFFE53935)

/**
 * A Kudos post card (cream surface, gold border) used both in the Highlight carousel and the
 * All-Kudos feed: sender↔receiver, time, title, content, hashtags, and a hearts/copy/detail bar.
 */
@Composable
fun KudoCard(
    kudo: Kudo,
    onLike: () -> Unit,
    onCopyLink: () -> Unit,
    onDetails: () -> Unit,
    onSender: () -> Unit,
    onReceiver: () -> Unit,
    modifier: Modifier = Modifier,
    contentMaxLines: Int = 3,
    centerTitle: Boolean = false,
    imageCount: Int = 0,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SaaCardCream)
            .border(1.dp, SaaButton, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PersonBlock(kudo.senderName, kudo.senderCode, kudo.senderHero.label, trailing = false, onClick = onSender, modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_outward),
                contentDescription = null,
                tint = InkColor,
                modifier = Modifier.size(18.dp),
            )
            PersonBlock(kudo.receiverName, kudo.receiverCode, kudo.receiverHero.label, trailing = true, onClick = onReceiver, modifier = Modifier.weight(1f))
        }

        HorizontalDivider(color = InkColor.copy(alpha = 0.12f))

        Text(kudo.time, color = MutedInk, fontFamily = Montserrat, fontSize = 10.sp)
        Text(
            text = appString(kudo.title),
            color = InkColor,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = if (centerTitle) TextAlign.Center else TextAlign.Start,
            modifier = if (centerTitle) Modifier.fillMaxWidth() else Modifier,
        )
        Text(
            text = appString(kudo.content),
            color = InkColor,
            fontFamily = Montserrat,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            maxLines = contentMaxLines,
            overflow = if (contentMaxLines == Int.MAX_VALUE) TextOverflow.Clip else TextOverflow.Ellipsis,
        )
        if (imageCount > 0) {
            ImageStrip(imageCount)
        }
        Text(
            text = kudo.hashtags.joinToString(" "),
            color = HashtagColor,
            fontFamily = Montserrat,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        HorizontalDivider(color = InkColor.copy(alpha = 0.12f))

        ActionBar(kudo, onLike, onCopyLink, onDetails)
    }
}

@Composable
private fun PersonBlock(name: String, code: String, hero: String, trailing: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val align = if (trailing) Alignment.End else Alignment.Start
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = align,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Avatar(name, size = 28.dp)
        Text(
            text = name,
            color = InkColor,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = if (trailing) TextAlign.End else TextAlign.Start,
        )
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(code, color = MutedInk, fontFamily = Montserrat, fontSize = 10.sp)
            HeroBadgeText(hero)
        }
    }
}

@Composable
private fun HeroBadgeText(label: String) {
    Text(
        text = label,
        color = SaaOnButton,
        fontFamily = Montserrat,
        fontSize = 9.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SaaButton)
            .padding(horizontal = 6.dp, vertical = 2.dp),
    )
}

/** Horizontal strip of attached-image placeholders (View Kudo detail). Tiles are evenly sized to the row. */
@Composable
private fun ImageStrip(count: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(count) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(InkColor.copy(alpha = 0.18f)),
            )
        }
    }
}

@Composable
private fun ActionBar(kudo: Kudo, onLike: () -> Unit, onCopyLink: () -> Unit, onDetails: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.clickable(onClick = onLike)) {
            Text(KudosViewModel.formatHearts(kudo.hearts), color = InkColor, fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 12.sp)
            Icon(
                painter = painterResource(R.drawable.ic_heart),
                contentDescription = null,
                tint = if (kudo.liked) HeartRed else MutedInk,
                modifier = Modifier.size(16.dp),
            )
        }
        ActionLink(appString(R.string.kudos_copy_link), R.drawable.ic_link, onCopyLink)
        ActionLink(appString(R.string.kudos_view_detail), R.drawable.ic_arrow_outward, onDetails)
    }
}

@Composable
private fun ActionLink(label: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable(onClick = onClick).height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(label, color = InkColor, fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 12.sp)
        Icon(painter = painterResource(iconRes), contentDescription = null, tint = InkColor, modifier = Modifier.size(14.dp))
    }
}
