package com.example.mysaaproject.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.notifications.NotificationsRepository
import com.example.mysaaproject.data.profile.ProfileRepository
import com.example.mysaaproject.ui.kudos.KudosViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Holds another member's profile, resolved from the `profileName` / `profileCode` nav arguments,
 * and the received-kudos list with a local heart like-toggle. The "thank-you" CTA and navigation
 * are handled by [OtherProfileRoute] / the nav host.
 */
class OtherProfileViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val name: String = savedStateHandle[ARG_NAME] ?: ""
    val code: String = savedStateHandle[ARG_CODE] ?: ""

    val profile = ProfileRepository.otherProfile(name, code)
    val receivedCount = ProfileRepository.otherReceivedKudos.size

    private val _kudos = MutableStateFlow(ProfileRepository.otherReceivedKudos)
    val kudos: StateFlow<List<Kudo>> = _kudos.asStateFlow()

    val unreadCount: StateFlow<Int> = NotificationsRepository.items
        .map { items -> items.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), 0)

    fun toggleLike(id: String) {
        _kudos.update { KudosViewModel.toggleLikeIn(it, id) }
    }

    companion object {
        const val ARG_NAME = "profileName"
        const val ARG_CODE = "profileCode"
    }
}
