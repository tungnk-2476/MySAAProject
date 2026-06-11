package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.GiftRecipient
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosStats
import com.example.mysaaproject.ui.components.SectionHeader
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp

/** All Kudos section (C): header + personal stats + top-10 recipients + kudo feed + "View all" link. */
@Composable
fun AllKudosSection(
    stats: KudosStats,
    recipients: List<GiftRecipient>,
    feed: List<Kudo>,
    onLike: (Kudo) -> Unit,
    onCopyLink: (Kudo) -> Unit,
    onDetails: (Kudo) -> Unit,
    onSender: (Kudo) -> Unit,
    onReceiver: (Kudo) -> Unit,
    onOpenSecretBox: () -> Unit,
    onRecipientClick: (GiftRecipient) -> Unit,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = CONTENT_INSET),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionHeader(
            eyebrow = appString(R.string.kudos_eyebrow),
            title = appString(R.string.kudos_all_title),
        )
        KudosStatsBlock(stats = stats, onOpenSecretBox = onOpenSecretBox)
        GiftRecipientsList(
            title = appString(R.string.kudos_top10_title),
            recipients = recipients,
            onRecipientClick = onRecipientClick,
        )
        // TODO: convert to a LazyColumn (or hoist into the screen's lazy list) before wiring real
        // paginated data — forEach composes every card upfront, fine only for this mock feed.
        feed.forEach { kudo ->
            KudoCard(
                kudo = kudo,
                onLike = { onLike(kudo) },
                onCopyLink = { onCopyLink(kudo) },
                onDetails = { onDetails(kudo) },
                onSender = { onSender(kudo) },
                onReceiver = { onReceiver(kudo) },
                contentMaxLines = 5,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onViewAll),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = appString(R.string.kudos_view_all),
                color = SaaOnDark,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_outward),
                contentDescription = null,
                tint = SaaOnDark,
                modifier = Modifier.padding(start = 6.dp).size(16.dp),
            )
        }
    }
}
