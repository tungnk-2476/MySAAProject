package com.example.mysaaproject.ui.kudos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Connects [AllKudosViewModel] to [AllKudosScreen]. Only the local heart like-toggle is wired;
 * copy-link, detail and profile actions are no-op TODO (out of scope). Navigation comes from the host.
 */
@Composable
fun AllKudosRoute(
    onBack: () -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    onOpenKudo: (String) -> Unit,
    onOpenProfile: (String, String) -> Unit,
    viewModel: AllKudosViewModel = viewModel(),
) {
    val kudos by viewModel.kudos.collectAsStateWithLifecycle()

    AllKudosScreen(
        kudos = kudos,
        onBack = onBack,
        onLike = { viewModel.toggleLike(it.id) },
        onCopyLink = { /* TODO: copy link + toast */ },
        onDetails = { onOpenKudo(it.id) },
        onSender = { onOpenProfile(it.senderName, it.senderCode) },
        onReceiver = { onOpenProfile(it.receiverName, it.receiverCode) },
        onSaaTab = onSaaTab,
        onKudosTab = onKudosTab,
        onAwardsTab = onAwardsTab,
        onProfileTab = onProfileTab,
    )
}
