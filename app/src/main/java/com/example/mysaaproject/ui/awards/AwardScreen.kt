package com.example.mysaaproject.ui.awards

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
import com.example.mysaaproject.data.awards.Award
import com.example.mysaaproject.data.awards.AwardsRepository
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.home.HomeHeader
import com.example.mysaaproject.ui.kudos.KudosPageBanner
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.theme.SaaBackground

private val CONTENT_INSET = 20.dp
private val HEADER_TOP_INSET = 76.dp

/**
 * Presentational Award Detail screen. State is hoisted ([AwardRoute]/[AwardViewModel]).
 * Fixed header → scrolling [KUDOS banner → highlight + category dropdown → award info → Kudos promo] →
 * fixed bottom nav (Awards active).
 */
@Composable
fun AwardScreen(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    unreadCount: Int,
    awards: List<Award>,
    selectedAward: Award,
    onSelectAward: (Award) -> Unit,
    onSearch: () -> Unit,
    onNotifications: () -> Unit,
    onKudosDetails: () -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onProfileTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    BannerArea()

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = CONTENT_INSET),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                    ) {
                        AwardHighlightBlock(awards = awards, selected = selectedAward, onSelect = onSelectAward)
                        AwardInfoBlock(award = selectedAward)
                    }

                    Spacer(Modifier.height(32.dp))
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = CONTENT_INSET)) {
                        KudosPromoBlock(onDetails = onKudosDetails)
                    }

                    Spacer(Modifier.height(120.dp))
                }
            }

            SaaBottomBar(
                active = BottomTab.AWARDS,
                // onAwards: already on the Awards screen — no-op.
                onSaa = onSaaTab, onAwards = {}, onKudos = onKudosTab, onProfile = onProfileTab,
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
private fun BannerArea() {
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
        ) {
            KudosPageBanner()
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 900)
@Composable
private fun AwardScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        AwardScreen(
            language = AppLanguage.VN, onLanguageSelected = {}, unreadCount = 1,
            awards = AwardsRepository.MOCK_AWARDS, selectedAward = AwardsRepository.MOCK_AWARDS.first(),
            onSelectAward = {}, onSearch = {}, onNotifications = {}, onKudosDetails = {},
            onSaaTab = {}, onKudosTab = {}, onProfileTab = {},
        )
    }
}
