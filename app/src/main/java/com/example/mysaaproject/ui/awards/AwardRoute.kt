package com.example.mysaaproject.ui.awards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaaproject.ui.locale.AppLanguage

/**
 * Connects [AwardViewModel] to [AwardScreen]. The category dropdown swaps the selected award; the Kudos
 * promo "Chi tiết" opens Kudos. Language and tab/header navigation come from the nav host.
 */
@Composable
fun AwardRoute(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenKudos: () -> Unit,
    onSaaTab: () -> Unit,
    onProfileTab: () -> Unit,
    viewModel: AwardViewModel = viewModel(),
) {
    val selectedAward by viewModel.selectedAward.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadCount.collectAsStateWithLifecycle()

    AwardScreen(
        language = language,
        onLanguageSelected = onLanguageSelected,
        unreadCount = unreadCount,
        awards = viewModel.awards,
        selectedAward = selectedAward,
        onSelectAward = { viewModel.selectAward(it.id) },
        onSearch = onOpenSearch,
        onNotifications = onOpenNotifications,
        onKudosDetails = onOpenKudos,
        onSaaTab = onSaaTab,
        onKudosTab = onOpenKudos,
        onProfileTab = onProfileTab,
    )
}
