package com.example.mysaaproject.ui.kudos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Connects [SendKudoViewModel] to [SendKudoScreen]. "Gửi đi" validates the form (no backend) and, on
 * success, returns to the previous screen; "Huỷ" / back also return. Navigation comes from the host.
 */
@Composable
fun SendKudoRoute(
    onClose: () -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    viewModel: SendKudoViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SendKudoScreen(
        uiState = uiState,
        titleOptions = viewModel.titleOptions,
        hashtagOptions = viewModel.hashtagOptions,
        onRecipientQueryChange = viewModel::onRecipientQueryChange,
        onSelectRecipient = viewModel::onSelectRecipient,
        onSelectTitle = viewModel::onSelectTitle,
        onMessageChange = viewModel::onMessageChange,
        onToggleHashtag = viewModel::onToggleHashtag,
        onAddImage = viewModel::onAddImage,
        onRemoveImage = viewModel::onRemoveImage,
        onToggleAnonymous = viewModel::onToggleAnonymous,
        onNicknameChange = viewModel::onNicknameChange,
        onCancel = onClose,
        onSend = { if (viewModel.validate()) onClose() },
        onSaaTab = onSaaTab,
        onKudosTab = onKudosTab,
        onAwardsTab = onAwardsTab,
        onProfileTab = onProfileTab,
    )
}
