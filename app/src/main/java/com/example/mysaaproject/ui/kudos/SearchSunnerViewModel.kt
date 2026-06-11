package com.example.mysaaproject.ui.kudos

import androidx.lifecycle.ViewModel
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.data.kudos.Recipient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Holds the Search Sunner screen state: the search query and the recent-search list. Realtime search
 * results are out of scope (only the default/Recent state is designed). Tapping a recent item opens a
 * Profile (no Profile screen yet — no-op in the route); the ✕ removes it from the list immediately.
 */
class SearchSunnerViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // Seeded from mock data; ✕-removals are local and reset when this screen leaves the back stack
    // (expected while data is mocked — a shared repository/backend will persist it later).
    private val _recent = MutableStateFlow(KudosRepository.recentSearches)
    val recent: StateFlow<List<Recipient>> = _recent.asStateFlow()

    fun onQueryChange(value: String) = _query.update { value }

    /** Remove a sunner from the recent-search list immediately (no confirmation). */
    fun removeRecent(id: String) = _recent.update { list -> list.filterNot { it.id == id } }
}
