package com.example.mysaaproject.ui.awards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysaaproject.data.awards.Award
import com.example.mysaaproject.data.awards.AwardsRepository
import com.example.mysaaproject.data.notifications.NotificationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Holds the Award Detail screen state: the list of award categories and the currently-selected one.
 * Selecting a category from the dropdown swaps the whole info block. Mock data — no API/loading state.
 */
class AwardViewModel : ViewModel() {

    val awards: List<Award> = AwardsRepository.MOCK_AWARDS

    private val _selectedAward = MutableStateFlow(
        awards.firstOrNull() ?: error("AwardViewModel requires at least one award"),
    )
    val selectedAward: StateFlow<Award> = _selectedAward.asStateFlow()

    /** Unread notification count for the shared header bell badge. */
    val unreadCount: StateFlow<Int> = NotificationsRepository.items
        .map { items -> items.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), 0)

    /** Select an award category by id; ignored if the id is unknown. */
    fun selectAward(id: String) {
        awards.firstOrNull { it.id == id }?.let { award -> _selectedAward.update { award } }
    }
}
