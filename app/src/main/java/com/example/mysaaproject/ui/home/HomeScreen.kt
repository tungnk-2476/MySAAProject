package com.example.mysaaproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.awards.Award
import com.example.mysaaproject.data.awards.AwardsRepository
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp
private val HERO_TOP_INSET = 76.dp // clears the fixed header

/**
 * Presentational Home dashboard. All state is hoisted (driven by [HomeRoute]/[HomeViewModel] in
 * production, static values in preview). Pixel-mapped from the MoMorph "[iOS] Home" frame:
 * fixed header → scrolling hero/awards/kudos → FAB → fixed bottom nav.
 */
@Composable
fun HomeScreen(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    countdown: Countdown,
    awardsState: AwardsState,
    isKudosAvailable: Boolean,
    unreadCount: Int,
    onSearch: () -> Unit,
    onNotifications: () -> Unit,
    onAboutAward: () -> Unit,
    onAboutKudos: () -> Unit,
    onAwardDetails: (Award) -> Unit,
    onRetryAwards: () -> Unit,
    onKudosDetails: () -> Unit,
    onWriteKudos: () -> Unit,
    onKudosShortcut: () -> Unit,
    onAwardsTab: () -> Unit,
    onKudosTab: () -> Unit,
    onProfileTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    HeroWithBackground(countdown, onAboutAward, onAboutKudos)

                    Text(
                        text = appString(R.string.home_theme_paragraph),
                        color = SaaOnDark,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = CONTENT_INSET),
                    )

                    Spacer(Modifier.height(32.dp))
                    AwardsSection(
                        state = awardsState,
                        onAwardDetails = onAwardDetails,
                        onRetry = onRetryAwards,
                    )

                    if (isKudosAvailable) {
                        Spacer(Modifier.height(32.dp))
                        KudosSection(onKudosDetails = onKudosDetails)
                    }

                    Spacer(Modifier.height(120.dp)) // clearance for FAB + bottom bar
                }

                HomeFab(
                    onWriteKudos = onWriteKudos,
                    onKudosShortcut = onKudosShortcut,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = CONTENT_INSET, bottom = 20.dp),
                )
            }

            SaaBottomBar(
                active = BottomTab.SAA,
                onSaa = {},
                onAwards = onAwardsTab,
                onKudos = onKudosTab,
                onProfile = onProfileTab,
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
                .background(
                    Brush.verticalGradient(listOf(SaaBackground, Color.Transparent)),
                )
                .statusBarsPadding(),
        )
    }
}

@Composable
private fun HeroWithBackground(
    countdown: Countdown,
    onAboutAward: () -> Unit,
    onAboutKudos: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.matchParentSize(),
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        0.6f to Color.Transparent,
                        1f to SaaBackground,
                    ),
                ),
        )
        HeroSection(
            countdown = countdown,
            onAboutAward = onAboutAward,
            onAboutKudos = onAboutKudos,
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = CONTENT_INSET, end = CONTENT_INSET, top = HERO_TOP_INSET, bottom = 24.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun HomeScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        HomeScreen(
            language = AppLanguage.VN,
            onLanguageSelected = {},
            countdown = Countdown(days = 20, hours = 20, minutes = 20, isBeforeEvent = true),
            awardsState = AwardsState.Success(AwardsRepository.MOCK_AWARDS),
            isKudosAvailable = true,
            unreadCount = 3,
            onSearch = {}, onNotifications = {}, onAboutAward = {}, onAboutKudos = {},
            onAwardDetails = {}, onRetryAwards = {}, onKudosDetails = {},
            onWriteKudos = {}, onKudosShortcut = {},
            onAwardsTab = {}, onKudosTab = {}, onProfileTab = {},
        )
    }
}
