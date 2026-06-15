package com.example.mysaaproject.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaaproject.ui.locale.AppLanguage

/**
 * Connects [OtherProfileViewModel] to [OtherProfileScreen]. The "thank-you" CTA opens the Send Kudo
 * flow; tapping a kudo sender/receiver opens that member's profile via [onOpenProfile]. Bottom-nav
 * and bell navigation come from the nav host.
 */
@Composable
fun OtherProfileRoute(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenSearch: () -> Unit,
    onSendThanks: () -> Unit,
    onSaaTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onKudosTab: () -> Unit,
    onProfileTab: () -> Unit,
    onOpenKudo: (String) -> Unit,
    onOpenProfile: (String, String) -> Unit,
    viewModel: OtherProfileViewModel = viewModel(),
) {
    val kudos by viewModel.kudos.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadCount.collectAsStateWithLifecycle()

    OtherProfileScreen(
        language = language,
        onLanguageSelected = onLanguageSelected,
        unreadCount = unreadCount,
        profile = viewModel.profile,
        kudos = kudos,
        receivedCount = viewModel.receivedCount,
        onSearch = onOpenSearch,
        onNotifications = onOpenNotifications,
        onSendThanks = onSendThanks,
        onLike = { viewModel.toggleLike(it.id) },
        onCopyLink = { /* TODO: copy link + toast */ },
        onDetails = { onOpenKudo(it.id) },
        onSender = { onOpenProfile(it.senderName, it.senderCode) },
        onReceiver = { onOpenProfile(it.receiverName, it.receiverCode) },
        onSaaTab = onSaaTab,
        onAwardsTab = onAwardsTab,
        onKudosTab = onKudosTab,
        onProfileTab = onProfileTab,
    )
}
