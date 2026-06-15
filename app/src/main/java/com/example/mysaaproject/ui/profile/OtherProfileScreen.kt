package com.example.mysaaproject.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.profile.ProfileRepository
import com.example.mysaaproject.data.profile.UserProfile
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.components.SectionHeader
import com.example.mysaaproject.ui.home.HomeHeader
import com.example.mysaaproject.ui.kudos.KudoCard
import com.example.mysaaproject.ui.kudos.SendKudosBar
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp

/**
 * Presentational other-member profile screen ("[iOS] Profile người khác"). State is hoisted (driven
 * by [OtherProfileRoute]/[OtherProfileViewModel]). Hero → "thank-you" CTA → received-kudos list →
 * fixed bottom nav. Differs from the own-profile screen: a CTA replaces the stats block and the
 * KUDOS count is a static label (no received/sent filter).
 */
@Composable
fun OtherProfileScreen(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    unreadCount: Int,
    profile: UserProfile,
    kudos: List<Kudo>,
    receivedCount: Int,
    onSearch: () -> Unit,
    onNotifications: () -> Unit,
    onSendThanks: () -> Unit,
    onLike: (Kudo) -> Unit,
    onCopyLink: (Kudo) -> Unit,
    onDetails: (Kudo) -> Unit,
    onSender: (Kudo) -> Unit,
    onReceiver: (Kudo) -> Unit,
    onSaaTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onKudosTab: () -> Unit,
    onProfileTab: () -> Unit,
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
                        SendKudosBar(onClick = onSendThanks, label = appString(R.string.profile_thank_you, profile.name))

                        SectionHeader(appString(R.string.kudos_eyebrow), appString(R.string.profile_kudos_title))

                        ReceivedCountPill(appString(R.string.profile_received_count, receivedCount))

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
                onSaa = onSaaTab, onAwards = onAwardsTab, onKudos = onKudosTab, onProfile = onProfileTab,
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

/** Static "Đã nhận N kudos" pill (other-profile KUDOS count — display-only, no filter). */
@Composable
private fun ReceivedCountPill(label: String) {
    Text(
        text = label,
        color = SaaOnDark,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, SaaBorderMuted, RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
    )
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun OtherProfileScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        OtherProfileScreen(
            language = AppLanguage.VN, onLanguageSelected = {}, unreadCount = 1,
            profile = ProfileRepository.otherProfile("Huỳnh Dương Xuân Nhật", "CEVC3"),
            kudos = ProfileRepository.otherReceivedKudos, receivedCount = 5,
            onSearch = {}, onNotifications = {}, onSendThanks = {},
            onLike = {}, onCopyLink = {}, onDetails = {}, onSender = {}, onReceiver = {},
            onSaaTab = {}, onAwardsTab = {}, onKudosTab = {}, onProfileTab = {},
        )
    }
}
