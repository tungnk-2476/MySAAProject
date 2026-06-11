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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Holds the Kudos screen's mock data and its stateful interactions: a local heart like-toggle
 * (TC_IOS_KUDOS_FUN_015/018) and the Highlight single-select Hashtag/Phòng ban filters. Secret box
 * and navigation are no-op (out of scope).
 */
class KudosViewModel : ViewModel() {

    /** Full (unfiltered) highlight list; likes mutate this base, filters derive [highlightKudos]. */
    private val _highlightKudos = MutableStateFlow(KudosRepository.highlightKudos)

    private val _feedKudos = MutableStateFlow(KudosRepository.feedKudos)
    val feedKudos: StateFlow<List<Kudo>> = _feedKudos.asStateFlow()

    val stats = KudosRepository.stats
    val recipients = KudosRepository.recipients
    val spotlightCount = KudosRepository.spotlightCount
    val spotlightNames = KudosRepository.spotlightNames

    val departments = KudosRepository.departments
    val hashtagOptions = KudosRepository.hashtagOptions

    private val _selectedDepartment = MutableStateFlow<String?>(null)
    val selectedDepartment: StateFlow<String?> = _selectedDepartment.asStateFlow()

    private val _selectedHashtag = MutableStateFlow<String?>(null)
    val selectedHashtag: StateFlow<String?> = _selectedHashtag.asStateFlow()

    /** Highlight carousel after applying the active department + hashtag filters. */
    val highlightKudos: StateFlow<List<Kudo>> =
        combine(_highlightKudos, _selectedDepartment, _selectedHashtag) { list, dept, tag ->
            filterKudos(list, dept, tag)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            filterKudos(KudosRepository.highlightKudos, department = null, hashtag = null),
        )

    /** Unread notification count for the shared header bell badge. */
    val unreadCount: StateFlow<Int> = NotificationsRepository.items
        .map { items -> items.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), 0)

    /** Toggle like locally: flips [Kudo.liked] and adjusts the heart count by one. */
    fun toggleLike(id: String) {
        _highlightKudos.update { toggleLikeIn(it, id) }
        _feedKudos.update { toggleLikeIn(it, id) }
    }

    /** Single-select department filter; selecting the active value again clears the filter. */
    fun selectDepartment(value: String) {
        _selectedDepartment.update { if (it == value) null else value }
    }

    /** Single-select hashtag filter; selecting the active value again clears the filter. */
    fun selectHashtag(value: String) {
        _selectedHashtag.update { if (it == value) null else value }
    }

    companion object {
        /** 1000 → "1.000" (dot thousands separator, matching the design). */
        fun formatHearts(value: Int): String = "%,d".format(value).replace(",", ".")

        /** Pure like-toggle over a kudo list (unit-testable): flips `liked` and ±1 heart for [id]. */
        fun toggleLikeIn(list: List<Kudo>, id: String): List<Kudo> = list.map { k ->
            if (k.id != id) k
            else k.copy(liked = !k.liked, hearts = k.hearts + if (k.liked) -1 else 1)
        }

        /** Pure highlight filter (unit-testable): keeps kudos matching the active dept AND hashtag (null = any). */
        fun filterKudos(list: List<Kudo>, department: String?, hashtag: String?): List<Kudo> =
            list.filter { k ->
                (department == null || k.department == department) &&
                    (hashtag == null || hashtag in k.hashtags)
            }
    }
}
