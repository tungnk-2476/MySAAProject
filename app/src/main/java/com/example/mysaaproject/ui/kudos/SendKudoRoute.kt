package com.example.mysaaproject.ui.kudos

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.LocalLocalizedContext

/**
 * Connects [SendKudoViewModel] to [SendKudoScreen]. "Gửi đi" validates the form and, on success,
 * appends the kudo to the shared feed, shows a confirmation toast, and routes to the Kudos list via
 * [onSent] so the new kudo is visible. "Huỷ" / back return without sending.
 */
@Composable
fun SendKudoRoute(
    onClose: () -> Unit,
    onSent: () -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    viewModel: SendKudoViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalLocalizedContext.current

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
        onSend = {
            if (viewModel.submit()) {
                Toast.makeText(context, context.getString(R.string.send_kudo_success), Toast.LENGTH_SHORT).show()
                onSent()
            }
        },
        onSaaTab = onSaaTab,
        onKudosTab = onKudosTab,
        onAwardsTab = onAwardsTab,
        onProfileTab = onProfileTab,
    )
}
