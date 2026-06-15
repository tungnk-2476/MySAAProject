package com.example.mysaaproject.ui.kudos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Connects [ViewKudoViewModel] to [ViewKudoScreen]. Only the local heart like-toggle is wired;
 * copy-link and profile actions are no-op TODO (out of scope). Navigation comes from the host.
 */
@Composable
fun ViewKudoRoute(
    onBack: () -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    onOpenProfile: (String, String) -> Unit,
    viewModel: ViewKudoViewModel = viewModel(),
) {
    val kudo by viewModel.kudo.collectAsStateWithLifecycle()

    ViewKudoScreen(
        kudo = kudo,
        onBack = onBack,
        onLike = { viewModel.toggleLike() },
        onCopyLink = { /* TODO: copy link + toast */ },
        onSender = { kudo?.let { onOpenProfile(it.senderName, it.senderCode) } },
        onReceiver = { kudo?.let { onOpenProfile(it.receiverName, it.receiverCode) } },
        onSaaTab = onSaaTab,
        onKudosTab = onKudosTab,
        onAwardsTab = onAwardsTab,
        onProfileTab = onProfileTab,
    )
}
