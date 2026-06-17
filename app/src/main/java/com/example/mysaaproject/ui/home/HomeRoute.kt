package com.example.mysaaproject.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaaproject.ui.locale.AppLanguage

/**
 * Connects [HomeViewModel] to the presentational [HomeScreen]. Only /home is in scope, so every
 * off-screen navigation target is a no-op placeholder (TODO) until those screens exist. Language
 * state is owned at the app level and passed through.
 */
@Composable
fun HomeRoute(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    // Logout currently has no designed UI entry; the plumbing is retained for a future
    // Profile/settings sign-out action. The Profile tab now opens the Profile screen.
    @Suppress("UNUSED_PARAMETER") onLogout: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenKudos: () -> Unit,
    onOpenAwards: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenSendKudo: () -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val countdown by viewModel.countdown.collectAsStateWithLifecycle()
    val awardsState by viewModel.awardsState.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadCount.collectAsStateWithLifecycle()

    HomeScreen(
        language = language,
        onLanguageSelected = onLanguageSelected,
        countdown = countdown,
        awardsState = awardsState,
        isKudosAvailable = viewModel.isKudosAvailable,
        unreadCount = unreadCount,
        onSearch = { /* TODO: navigate to Search screen */ },
        onNotifications = onOpenNotifications,
        onAboutAward = onOpenAwards,
        onAboutKudos = { /* TODO: navigate to Kudos overview */ },
        onAwardDetails = { onOpenAwards() },
        onRetryAwards = viewModel::retryAwards,
        onKudosDetails = { /* TODO: navigate to Kudos detail */ },
        onWriteKudos = onOpenSendKudo,
        onKudosShortcut = onOpenKudos,
        onAwardsTab = onOpenAwards,
        onKudosTab = onOpenKudos,
        onProfileTab = onOpenProfile,
    )
}
