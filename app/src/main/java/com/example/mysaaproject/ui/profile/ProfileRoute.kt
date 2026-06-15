package com.example.mysaaproject.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaaproject.ui.locale.AppLanguage

/**
 * Connects [ProfileViewModel] to [ProfileScreen] (own profile). The received/sent filter and the
 * heart like-toggle are wired; secret box / copy link are no-op TODO. Tapping a kudo sender or
 * receiver opens that member's profile via [onOpenProfile]. Bottom-nav and bell navigation come
 * from the nav host.
 */
@Composable
fun ProfileRoute(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenSearch: () -> Unit,
    onSaaTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onKudosTab: () -> Unit,
    onOpenKudo: (String) -> Unit,
    onOpenProfile: (String, String) -> Unit,
    viewModel: ProfileViewModel = viewModel(),
) {
    val kudos by viewModel.kudos.collectAsStateWithLifecycle()
    val filter by viewModel.filter.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadCount.collectAsStateWithLifecycle()

    ProfileScreen(
        language = language,
        onLanguageSelected = onLanguageSelected,
        unreadCount = unreadCount,
        profile = viewModel.profile,
        kudos = kudos,
        filter = filter,
        receivedCount = viewModel.receivedCount,
        sentCount = viewModel.sentCount,
        onSelectFilter = viewModel::selectFilter,
        onSearch = onOpenSearch,
        onNotifications = onOpenNotifications,
        onLike = { viewModel.toggleLike(it.id) },
        onCopyLink = { /* TODO: copy link + toast */ },
        onDetails = { onOpenKudo(it.id) },
        onSender = { onOpenProfile(it.senderName, it.senderCode) },
        onReceiver = { onOpenProfile(it.receiverName, it.receiverCode) },
        onOpenSecretBox = { /* TODO: open Secret Box flow */ },
        onSaaTab = onSaaTab,
        onAwardsTab = onAwardsTab,
        onKudosTab = onKudosTab,
    )
}
