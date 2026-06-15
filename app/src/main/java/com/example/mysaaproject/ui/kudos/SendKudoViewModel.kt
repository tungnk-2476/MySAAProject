package com.example.mysaaproject.ui.kudos

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.data.kudos.Recipient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Immutable UI state for the New Kudo form. */
data class SendKudoUiState(
    val recipientQuery: String = "",
    val selectedRecipient: Recipient? = null,
    val recipientResults: List<Recipient> = emptyList(),
    @param:StringRes val title: Int? = null,
    val message: String = "",
    val selectedHashtags: List<String> = emptyList(),
    val imageCount: Int = 0,
    val anonymous: Boolean = false,
    val nickname: String = "",
    val recipientError: Boolean = false,
    val titleError: Boolean = false,
    val hashtagError: Boolean = false,
    val nicknameError: Boolean = false,
)

/**
 * Holds the New Kudo form state and the user interactions in scope (recipient search/select, danh hiệu,
 * message, hashtag multi-select, images, anonymous + nickname). [validate] gates the "Gửi đi" action;
 * there is no backend, so a valid submit just lets the route navigate back.
 */
class SendKudoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SendKudoUiState())
    val uiState: StateFlow<SendKudoUiState> = _uiState.asStateFlow()

    val titleOptions = KudosRepository.titleOptions
    val hashtagOptions = KudosRepository.sendHashtagOptions

    fun onRecipientQueryChange(query: String) = _uiState.update {
        it.copy(
            recipientQuery = query,
            selectedRecipient = null,
            recipientResults = searchRecipients(query),
            recipientError = false,
        )
    }

    fun onSelectRecipient(recipient: Recipient) = _uiState.update {
        it.copy(
            selectedRecipient = recipient,
            recipientQuery = recipient.name,
            recipientResults = emptyList(),
            recipientError = false,
        )
    }

    fun onSelectTitle(@StringRes title: Int) = _uiState.update { it.copy(title = title, titleError = false) }

    fun onMessageChange(message: String) = _uiState.update { it.copy(message = message) }

    fun onToggleHashtag(tag: String) = _uiState.update {
        it.copy(selectedHashtags = toggleHashtag(it.selectedHashtags, tag), hashtagError = false)
    }

    fun onAddImage() = _uiState.update { if (it.imageCount < MAX_IMAGES) it.copy(imageCount = it.imageCount + 1) else it }

    fun onRemoveImage() = _uiState.update { if (it.imageCount > 0) it.copy(imageCount = it.imageCount - 1) else it }

    fun onToggleAnonymous() = _uiState.update { it.copy(anonymous = !it.anonymous, nicknameError = false) }

    fun onNicknameChange(nickname: String) = _uiState.update { it.copy(nickname = nickname, nicknameError = false) }

    /** Validate required fields, set inline error flags, and return whether the form may be submitted. */
    fun validate(): Boolean {
        var valid = false
        _uiState.update { s ->
            val recipientError = s.selectedRecipient == null
            val titleError = s.title == null
            val hashtagError = s.selectedHashtags.isEmpty()
            val nicknameError = s.anonymous && s.nickname.isBlank()
            valid = !recipientError && !titleError && !hashtagError && !nicknameError
            s.copy(
                recipientError = recipientError,
                titleError = titleError,
                hashtagError = hashtagError,
                nicknameError = nicknameError,
            )
        }
        return valid
    }

    private fun searchRecipients(query: String): List<Recipient> {
        val q = query.trim()
        if (q.isEmpty()) return emptyList()
        return KudosRepository.kudoRecipients.filter { it.name.contains(q, ignoreCase = true) }
    }

    companion object {
        const val MAX_HASHTAGS = 5
        const val MAX_IMAGES = 5

        /** Pure hashtag multi-select toggle: remove if present, add if under [max], else unchanged. */
        fun toggleHashtag(current: List<String>, tag: String, max: Int = MAX_HASHTAGS): List<String> = when {
            tag in current -> current - tag
            current.size >= max -> current
            else -> current + tag
        }
    }
}
