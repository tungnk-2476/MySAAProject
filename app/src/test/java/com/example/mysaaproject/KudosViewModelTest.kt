package com.example.mysaaproject

import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.ui.kudos.KudosViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Verifies the local heart like-toggle logic (TC_IOS_KUDOS_FUN_015/018) and heart formatting. */
class KudosViewModelTest {

    @Test
    fun toggleLike_likesThenUnlikes_adjustingHeartCount() {
        val seed = KudosRepository.highlightKudos
        val target = seed.first()
        assertFalse(target.liked)

        val afterLike = KudosViewModel.toggleLikeIn(seed, target.id).first { it.id == target.id }
        assertTrue(afterLike.liked)
        assertEquals(target.hearts + 1, afterLike.hearts)

        val afterUnlike = KudosViewModel.toggleLikeIn(
            KudosViewModel.toggleLikeIn(seed, target.id),
            target.id,
        ).first { it.id == target.id }
        assertFalse(afterUnlike.liked)
        assertEquals(target.hearts, afterUnlike.hearts)
    }

    @Test
    fun toggleLike_onlyAffectsMatchingId() {
        val seed = KudosRepository.highlightKudos
        val result = KudosViewModel.toggleLikeIn(seed, seed.first().id)
        assertEquals(seed.drop(1), result.drop(1))
    }

    @Test
    fun formatHearts_usesDotThousandsSeparator() {
        assertEquals("1.000", KudosViewModel.formatHearts(1000))
    }
}
