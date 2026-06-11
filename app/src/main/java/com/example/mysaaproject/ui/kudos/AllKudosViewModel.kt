package com.example.mysaaproject.ui.kudos

import androidx.lifecycle.ViewModel
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Holds the dedicated "All Kudos" screen's mock feed and the one stateful interaction in scope:
 * a local heart like-toggle (reuses [KudosViewModel.toggleLikeIn]). Other card actions are no-op.
 *
 * Like state here is intentionally independent of the Kudos screen's feeds (separate ViewModels over
 * separate mock lists) — they will converge once a shared Kudos repository/backend replaces the mocks.
 */
class AllKudosViewModel : ViewModel() {

    private val _kudos = MutableStateFlow(KudosRepository.allKudos)
    val kudos: StateFlow<List<Kudo>> = _kudos.asStateFlow()

    /** Toggle like locally: flips [Kudo.liked] and adjusts the heart count by one. */
    fun toggleLike(id: String) {
        _kudos.update { KudosViewModel.toggleLikeIn(it, id) }
    }
}
