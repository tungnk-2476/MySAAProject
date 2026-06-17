package com.example.mysaaproject.ui.kudos

import androidx.lifecycle.ViewModel
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import kotlinx.coroutines.flow.StateFlow

/**
 * Holds the dedicated "All Kudos" screen's feed and the one stateful interaction in scope: a heart
 * like-toggle. Both the feed and likes are backed by the shared [KudosRepository.feed], so kudos
 * composed on the New Kudo screen appear here, and likes stay consistent with the Kudos tab.
 */
class AllKudosViewModel : ViewModel() {

    val kudos: StateFlow<List<Kudo>> = KudosRepository.feed

    /** Toggle like on the shared feed: flips [Kudo.liked] and adjusts the heart count by one. */
    fun toggleLike(id: String) = KudosRepository.toggleLike(id)
}
