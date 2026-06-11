package com.example.mysaaproject.ui.kudos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaaproject.ui.locale.AppLanguage

/**
 * Connects [KudosViewModel] to [KudosScreen]. Only the local heart like-toggle is wired; filters,
 * Send Kudos, Secret Box, detail/profile/view-all are no-op TODO (out of scope). Language and the
 * bell/Home navigation come from the nav host.
 */
@Composable
fun KudosRoute(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onOpenNotifications: () -> Unit,
    onSaaTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    onOpenAllKudos: () -> Unit,
    onOpenKudo: (String) -> Unit,
    viewModel: KudosViewModel = viewModel(),
) {
    val highlightKudos by viewModel.highlightKudos.collectAsStateWithLifecycle()
    val feedKudos by viewModel.feedKudos.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadCount.collectAsStateWithLifecycle()
    val selectedHashtag by viewModel.selectedHashtag.collectAsStateWithLifecycle()
    val selectedDepartment by viewModel.selectedDepartment.collectAsStateWithLifecycle()

    KudosScreen(
        language = language,
        onLanguageSelected = onLanguageSelected,
        unreadCount = unreadCount,
        highlightKudos = highlightKudos,
        feedKudos = feedKudos,
        stats = viewModel.stats,
        recipients = viewModel.recipients,
        spotlightCount = viewModel.spotlightCount,
        spotlightNames = viewModel.spotlightNames,
        hashtagOptions = viewModel.hashtagOptions,
        departments = viewModel.departments,
        selectedHashtag = selectedHashtag,
        selectedDepartment = selectedDepartment,
        onSearch = { /* TODO: navigate to Search */ },
        onNotifications = onOpenNotifications,
        onLike = { viewModel.toggleLike(it.id) },
        onCopyLink = { /* TODO: copy link + toast */ },
        onDetails = { onOpenKudo(it.id) },
        onSender = { /* TODO: navigate to sender profile */ },
        onReceiver = { /* TODO: navigate to receiver profile */ },
        onSendKudos = { /* TODO: open Send Kudos flow */ },
        onSelectHashtag = viewModel::selectHashtag,
        onSelectDepartment = viewModel::selectDepartment,
        onOpenSecretBox = { /* TODO: open Secret Box flow */ },
        onRecipientClick = { /* TODO: navigate to recipient profile */ },
        onViewAll = onOpenAllKudos,
        onSaaTab = onSaaTab,
        onAwardsTab = onAwardsTab,
        onProfileTab = onProfileTab,
    )
}
