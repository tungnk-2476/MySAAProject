package com.example.mysaaproject.ui.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Connects [NotificationsViewModel] to [NotificationsScreen]. Tapping an item marks it read
 * (which updates the shared unread count → Home bell badge); per-type detail navigation is a
 * no-op TODO until those screens exist.
 */
@Composable
fun NotificationsRoute(
    onBack: () -> Unit,
    onOpenCommunityStandards: () -> Unit,
    viewModel: NotificationsViewModel = viewModel(),
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    NotificationsScreen(
        items = items,
        onBack = onBack,
        onItemClick = { item ->
            if (!item.isRead) viewModel.markRead(item.id)
            // TODO: navigate to the per-type detail screen (kudo, secret box, profile, admin review…)
        },
        onMarkAllRead = viewModel::markAllRead,
        onCommunityStandards = onOpenCommunityStandards,
    )
}
