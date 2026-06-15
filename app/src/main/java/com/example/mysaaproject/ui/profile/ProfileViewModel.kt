package com.example.mysaaproject.ui.profile

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/** Own-profile KUDOS list filter: kudos the user received vs. those they sent ("Đã nhận / Đã gửi"). */
enum class KudoFilter { RECEIVED, SENT }

/**
 * Holds the own-profile screen's mock data and its stateful interactions: the received/sent kudos
 * filter and a local heart like-toggle (reusing [KudosViewModel.toggleLikeIn]). Secret box, copy
 * link and avatar/badge taps are no-op (out of scope, per clarifications).
 */
class ProfileViewModel : ViewModel() {

    val profile = ProfileRepository.ownProfile
    val receivedCount = ProfileRepository.receivedKudos.size
    val sentCount = ProfileRepository.sentKudos.size

    private val _filter = MutableStateFlow(KudoFilter.RECEIVED)
    val filter: StateFlow<KudoFilter> = _filter.asStateFlow()

    private val _received = MutableStateFlow(ProfileRepository.receivedKudos)
    private val _sent = MutableStateFlow(ProfileRepository.sentKudos)

    /** Kudos shown under the KUDOS heading, switched by the active [filter]. */
    val kudos: StateFlow<List<Kudo>> =
        combine(_filter, _received, _sent) { f, received, sent -> if (f == KudoFilter.RECEIVED) received else sent }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), ProfileRepository.receivedKudos)

    /** Unread notification count for the shared header bell badge. */
    val unreadCount: StateFlow<Int> = NotificationsRepository.items
        .map { items -> items.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), 0)

    fun selectFilter(value: KudoFilter) {
        _filter.value = value
    }

    /** Toggle like locally across both lists so the state survives a filter switch. */
    fun toggleLike(id: String) {
        _received.update { KudosViewModel.toggleLikeIn(it, id) }
        _sent.update { KudosViewModel.toggleLikeIn(it, id) }
    }
}
