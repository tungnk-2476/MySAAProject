package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.components.SectionHeader
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp

/**
 * Presentational dedicated "All Kudos" screen: back top bar → "ALL KUDOS" header → scrollable list
 * of [KudoCard] → fixed bottom nav (Kudos active). State is hoisted (driven by [AllKudosRoute]).
 */
@Composable
fun AllKudosScreen(
    kudos: List<Kudo>,
    onBack: () -> Unit,
    onLike: (Kudo) -> Unit,
    onCopyLink: (Kudo) -> Unit,
    onDetails: (Kudo) -> Unit,
    onSender: (Kudo) -> Unit,
    onReceiver: (Kudo) -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(modifier = Modifier.fillMaxSize().background(SaaBackground.copy(alpha = 0.82f)))

        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            TopBar(onBack = onBack)
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = CONTENT_INSET, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    SectionHeader(
                        eyebrow = appString(R.string.kudos_eyebrow),
                        title = appString(R.string.kudos_all_title),
                    )
                }
                // TODO: render an empty-state placeholder when `kudos` is empty (real-data phase).
                items(kudos, key = { it.id }) { kudo ->
                    KudoCard(
                        kudo = kudo,
                        onLike = { onLike(kudo) },
                        onCopyLink = { onCopyLink(kudo) },
                        onDetails = { onDetails(kudo) },
                        onSender = { onSender(kudo) },
                        onReceiver = { onReceiver(kudo) },
                        contentMaxLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
            SaaBottomBar(
                active = BottomTab.KUDOS,
                onSaa = onSaaTab,
                onAwards = onAwardsTab,
                onKudos = onKudosTab,
                onProfile = onProfileTab,
            )
        }
    }
}

@Composable
private fun TopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(44.dp).padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = appString(R.string.cd_back),
                tint = SaaOnDark,
                modifier = Modifier.size(24.dp),
            )
        }
        Text(
            text = appString(R.string.kudos_all_screen_title),
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun AllKudosScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        AllKudosScreen(
            kudos = KudosRepository.allKudos,
            onBack = {}, onLike = {}, onCopyLink = {}, onDetails = {}, onSender = {}, onReceiver = {},
            onSaaTab = {}, onKudosTab = {}, onAwardsTab = {}, onProfileTab = {},
        )
    }
}
