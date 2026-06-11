package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.GiftRecipient
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.data.kudos.KudosStats
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.components.SectionHeader
import com.example.mysaaproject.ui.home.HomeHeader
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.SaaBackground

private val CONTENT_INSET = 20.dp
private val HEADER_TOP_INSET = 76.dp

/**
 * Presentational Sun* Kudos screen. State is hoisted (driven by [KudosRoute]/[KudosViewModel]).
 * Fixed header → scrolling [banner → Highlight carousel → Spotlight → All Kudos] → fixed bottom nav.
 */
@Composable
fun KudosScreen(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    unreadCount: Int,
    highlightKudos: List<Kudo>,
    feedKudos: List<Kudo>,
    stats: KudosStats,
    recipients: List<GiftRecipient>,
    spotlightCount: Int,
    spotlightNames: List<String>,
    onSearch: () -> Unit,
    onNotifications: () -> Unit,
    onLike: (Kudo) -> Unit,
    onCopyLink: (Kudo) -> Unit,
    onDetails: (Kudo) -> Unit,
    onSender: (Kudo) -> Unit,
    onReceiver: (Kudo) -> Unit,
    onSendKudos: () -> Unit,
    onHashtagFilter: () -> Unit,
    onDeptFilter: () -> Unit,
    onOpenSecretBox: () -> Unit,
    onRecipientClick: (GiftRecipient) -> Unit,
    onViewAll: () -> Unit,
    onSaaTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    BannerArea(onSendKudos)

                    HighlightSection(
                        kudos = highlightKudos,
                        onLike = onLike, onCopyLink = onCopyLink, onDetails = onDetails,
                        onSender = onSender, onReceiver = onReceiver,
                        onHashtagFilter = onHashtagFilter, onDeptFilter = onDeptFilter,
                    )

                    Spacer(Modifier.height(32.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = CONTENT_INSET),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        SectionHeader(appString(R.string.kudos_eyebrow), appString(R.string.kudos_spotlight_title))
                        SpotlightBoard(count = spotlightCount, names = spotlightNames)
                    }

                    Spacer(Modifier.height(32.dp))
                    AllKudosSection(
                        stats = stats, recipients = recipients, feed = feedKudos,
                        onLike = onLike, onCopyLink = onCopyLink, onDetails = onDetails,
                        onSender = onSender, onReceiver = onReceiver,
                        onOpenSecretBox = onOpenSecretBox, onRecipientClick = onRecipientClick, onViewAll = onViewAll,
                    )

                    Spacer(Modifier.height(120.dp))
                }
            }

            SaaBottomBar(
                active = BottomTab.KUDOS,
                onSaa = onSaaTab, onAwards = onAwardsTab, onKudos = {}, onProfile = onProfileTab,
            )
        }

        HomeHeader(
            language = language,
            onLanguageSelected = onLanguageSelected,
            unreadCount = unreadCount,
            onSearchClick = onSearch,
            onNotificationsClick = onNotifications,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Brush.verticalGradient(listOf(SaaBackground, Color.Transparent)))
                .statusBarsPadding(),
        )
    }
}

@Composable
private fun BannerArea(onSendKudos: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.matchParentSize(),
        )
        Box(modifier = Modifier.matchParentSize().background(Brush.verticalGradient(0.55f to Color.Transparent, 1f to SaaBackground)))
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = CONTENT_INSET, end = CONTENT_INSET, top = HEADER_TOP_INSET, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            KudosPageBanner()
            SendKudosBar(onClick = onSendKudos)
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun KudosScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        KudosScreen(
            language = AppLanguage.VN, onLanguageSelected = {}, unreadCount = 1,
            highlightKudos = KudosRepository.highlightKudos, feedKudos = KudosRepository.feedKudos,
            stats = KudosRepository.stats, recipients = KudosRepository.recipients,
            spotlightCount = KudosRepository.spotlightCount, spotlightNames = KudosRepository.spotlightNames,
            onSearch = {}, onNotifications = {}, onLike = {}, onCopyLink = {}, onDetails = {},
            onSender = {}, onReceiver = {}, onSendKudos = {}, onHashtagFilter = {}, onDeptFilter = {},
            onOpenSecretBox = {}, onRecipientClick = {}, onViewAll = {},
            onSaaTab = {}, onAwardsTab = {}, onProfileTab = {},
        )
    }
}
