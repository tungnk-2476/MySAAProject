package com.example.mysaaproject.ui.kudos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Holds the single kudo shown on the View Kudo detail screen, resolved by the `kudoId` nav argument,
 * plus the local heart like-toggle (reuses [KudosViewModel.toggleLikeIn]).
 *
 * Like state here is screen-local (mock); it stays independent of the list feeds until a shared Kudos
 * repository/backend replaces the mocks.
 */
class ViewKudoViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val kudoId: String = checkNotNull(savedStateHandle[KUDO_ID_ARG]) { "Missing $KUDO_ID_ARG" }

    private val _kudo = MutableStateFlow(KudosRepository.findById(kudoId))
    val kudo: StateFlow<Kudo?> = _kudo.asStateFlow()

    /** Toggle like locally: flips [Kudo.liked] and adjusts the heart count by one. */
    fun toggleLike() {
        _kudo.update { it?.let(KudosViewModel::applyLikeToggle) }
    }

    companion object {
        const val KUDO_ID_ARG = "kudoId"
    }
}
