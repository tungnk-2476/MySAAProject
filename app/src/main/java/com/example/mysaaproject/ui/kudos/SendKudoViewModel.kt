package com.example.mysaaproject.ui.kudos

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.HeroLevel
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.data.kudos.Recipient
import com.example.mysaaproject.data.profile.ProfileRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

    /**
     * Validate the form and, on success, compose the kudo and prepend it to the shared feed so it
     * appears at the top of the Kudos list. Returns whether the submit succeeded (the route navigates
     * back only on success).
     */
    fun submit(): Boolean {
        if (!validate()) return false
        KudosRepository.addKudo(composeKudo(_uiState.value))
        return true
    }

    /** Build a [Kudo] from the validated form state (sender = logged-in user, receiver = selection). */
    private fun composeKudo(state: SendKudoUiState): Kudo {
        val me = ProfileRepository.ownProfile
        val recipient = requireNotNull(state.selectedRecipient)
        val displaySender = if (state.anonymous) state.nickname else me.name
        return Kudo(
            id = "u${System.currentTimeMillis()}",
            senderName = displaySender,
            senderCode = if (state.anonymous) "" else me.code,
            senderHero = me.hero,
            receiverName = recipient.name,
            receiverCode = recipient.unit,
            receiverHero = HeroLevel.RISING,
            time = TIME_FORMAT.format(Date()),
            title = requireNotNull(state.title),
            content = R.string.kudo_sample_content, // fallback; contentText is shown when present
            contentText = state.message,
            hashtags = state.selectedHashtags,
            hearts = 0,
            department = recipient.unit,
            imageCount = state.imageCount,
        )
    }

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

        /** Kudo timestamp format, matching the mock feed ("HH:mm - MM/dd/yyyy"). */
        private val TIME_FORMAT = SimpleDateFormat("HH:mm - MM/dd/yyyy", Locale.US)

        /** Pure hashtag multi-select toggle: remove if present, add if under [max], else unchanged. */
        fun toggleHashtag(current: List<String>, tag: String, max: Int = MAX_HASHTAGS): List<String> = when {
            tag in current -> current - tag
            current.size >= max -> current
            else -> current + tag
        }
    }
}
