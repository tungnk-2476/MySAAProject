package com.example.mysaaproject.ui.profile

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
import androidx.compose.ui.unit.dp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.profile.ProfileRepository
import com.example.mysaaproject.data.profile.UserProfile
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.components.SectionHeader
import com.example.mysaaproject.ui.home.HomeHeader
import com.example.mysaaproject.ui.kudos.FilterDropdown
import com.example.mysaaproject.ui.kudos.KudoCard
import com.example.mysaaproject.ui.kudos.KudosStatsBlock
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.SaaBackground

private val CONTENT_INSET = 20.dp

/**
 * Presentational own-profile screen ("[iOS] Profile bản thân"). State is hoisted (driven by
 * [ProfileRoute]/[ProfileViewModel]). Fixed header → scrolling [hero → stats → KUDOS list] → fixed
 * bottom nav (Profile tab active).
 */
@Composable
fun ProfileScreen(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    unreadCount: Int,
    profile: UserProfile,
    kudos: List<Kudo>,
    filter: KudoFilter,
    receivedCount: Int,
    sentCount: Int,
    onSelectFilter: (KudoFilter) -> Unit,
    onSearch: () -> Unit,
    onNotifications: () -> Unit,
    onLike: (Kudo) -> Unit,
    onCopyLink: (Kudo) -> Unit,
    onDetails: (Kudo) -> Unit,
    onSender: (Kudo) -> Unit,
    onReceiver: (Kudo) -> Unit,
    onOpenSecretBox: () -> Unit,
    onSaaTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onKudosTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    ProfileHeroSection(profile = profile, iconCollectionLabel = appString(R.string.profile_icon_collection))

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = CONTENT_INSET),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        KudosStatsBlock(stats = profile.stats, onOpenSecretBox = onOpenSecretBox)

                        SectionHeader(appString(R.string.kudos_eyebrow), appString(R.string.profile_kudos_title))

                        val receivedLabel = "${appString(R.string.profile_filter_received)} ($receivedCount)"
                        val sentLabel = "${appString(R.string.profile_filter_sent)} ($sentCount)"
                        val selectedLabel = if (filter == KudoFilter.RECEIVED) receivedLabel else sentLabel
                        FilterDropdown(
                            defaultLabel = selectedLabel,
                            options = listOf(receivedLabel, sentLabel),
                            selected = selectedLabel,
                            onSelect = { onSelectFilter(if (it == receivedLabel) KudoFilter.RECEIVED else KudoFilter.SENT) },
                        )

                        kudos.forEach { kudo ->
                            KudoCard(
                                kudo = kudo,
                                onLike = { onLike(kudo) },
                                onCopyLink = { onCopyLink(kudo) },
                                onDetails = { onDetails(kudo) },
                                onSender = { onSender(kudo) },
                                onReceiver = { onReceiver(kudo) },
                                centerTitle = true,
                                imageCount = kudo.imageCount,
                            )
                        }
                    }

                    Spacer(Modifier.height(120.dp))
                }
            }

            SaaBottomBar(
                active = BottomTab.PROFILE,
                onSaa = onSaaTab, onAwards = onAwardsTab, onKudos = onKudosTab, onProfile = {},
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

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun ProfileScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        ProfileScreen(
            language = AppLanguage.VN, onLanguageSelected = {}, unreadCount = 1,
            profile = ProfileRepository.ownProfile, kudos = ProfileRepository.receivedKudos,
            filter = KudoFilter.RECEIVED, receivedCount = 5, sentCount = 5,
            onSelectFilter = {}, onSearch = {}, onNotifications = {},
            onLike = {}, onCopyLink = {}, onDetails = {}, onSender = {}, onReceiver = {},
            onOpenSecretBox = {}, onSaaTab = {}, onAwardsTab = {}, onKudosTab = {},
        )
    }
}
