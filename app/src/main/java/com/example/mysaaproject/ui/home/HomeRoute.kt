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
    onLogout: () -> Unit,
    onOpenNotifications: () -> Unit,
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
        onAboutAward = { /* TODO: navigate to Awards overview */ },
        onAboutKudos = { /* TODO: navigate to Kudos overview */ },
        onAwardDetails = { /* TODO: navigate to Award detail */ },
        onRetryAwards = viewModel::retryAwards,
        onKudosDetails = { /* TODO: navigate to Kudos detail */ },
        onWriteKudos = { /* TODO: open WriteKudo form */ },
        onKudosShortcut = { /* TODO: navigate to Kudos feed */ },
        onAwardsTab = { /* TODO: navigate to Awards screen */ },
        onKudosTab = { /* TODO: navigate to Kudos screen */ },
        // TODO: replace with the Profile screen; interim logout entry so the flow stays reachable.
        onProfileTab = onLogout,
    )
}
