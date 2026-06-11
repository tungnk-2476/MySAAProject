package com.example.mysaaproject.ui.kudos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Connects [SearchSunnerViewModel] to [SearchSunnerScreen]. The recent-item ✕ removes locally; tapping
 * an item (Profile) and "View all" are no-op TODO (out of scope). Navigation comes from the host.
 */
@Composable
fun SearchSunnerRoute(
    onBack: () -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    viewModel: SearchSunnerViewModel = viewModel(),
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val recent by viewModel.recent.collectAsStateWithLifecycle()

    SearchSunnerScreen(
        query = query,
        recent = recent,
        onQueryChange = viewModel::onQueryChange,
        onBack = onBack,
        onItemClick = { /* TODO: navigate to sunner Profile */ },
        onRemove = { viewModel.removeRecent(it.id) },
        onViewAll = { /* TODO: expand full recent-search history */ },
        onSaaTab = onSaaTab,
        onKudosTab = onKudosTab,
        onAwardsTab = onAwardsTab,
        onProfileTab = onProfileTab,
    )
}
