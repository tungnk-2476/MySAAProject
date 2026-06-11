package com.example.mysaaproject.ui.kudos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.data.notifications.NotificationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Holds the Kudos screen's mock data and the one stateful interaction in scope: a local heart
 * like-toggle (TC_IOS_KUDOS_FUN_015/018). Filters, secret box, and navigation are no-op (out of scope).
 */
class KudosViewModel : ViewModel() {

    private val _highlightKudos = MutableStateFlow(KudosRepository.highlightKudos)
    val highlightKudos: StateFlow<List<Kudo>> = _highlightKudos.asStateFlow()

    private val _feedKudos = MutableStateFlow(KudosRepository.feedKudos)
    val feedKudos: StateFlow<List<Kudo>> = _feedKudos.asStateFlow()

    val stats = KudosRepository.stats
    val recipients = KudosRepository.recipients
    val spotlightCount = KudosRepository.spotlightCount
    val spotlightNames = KudosRepository.spotlightNames

    /** Unread notification count for the shared header bell badge. */
    val unreadCount: StateFlow<Int> = NotificationsRepository.items
        .map { items -> items.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), 0)

    /** Toggle like locally: flips [Kudo.liked] and adjusts the heart count by one. */
    fun toggleLike(id: String) {
        _highlightKudos.update { toggleLikeIn(it, id) }
        _feedKudos.update { toggleLikeIn(it, id) }
    }

    companion object {
        /** 1000 → "1.000" (dot thousands separator, matching the design). */
        fun formatHearts(value: Int): String = "%,d".format(value).replace(",", ".")

        /** Pure like-toggle over a kudo list (unit-testable): flips `liked` and ±1 heart for [id]. */
        fun toggleLikeIn(list: List<Kudo>, id: String): List<Kudo> = list.map { k ->
            if (k.id != id) k
            else k.copy(liked = !k.liked, hearts = k.hearts + if (k.liked) -1 else 1)
        }
    }
}
