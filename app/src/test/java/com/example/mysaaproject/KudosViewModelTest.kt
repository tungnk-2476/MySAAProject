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

    @Test
    fun filterKudos_nullFilters_returnsAll() {
        val all = KudosRepository.highlightKudos
        assertEquals(all, KudosViewModel.filterKudos(all, department = null, hashtag = null))
    }

    @Test
    fun filterKudos_byDepartment_keepsOnlyThatDepartment() {
        val all = KudosRepository.highlightKudos
        val dept = all.first().department
        val result = KudosViewModel.filterKudos(all, department = dept, hashtag = null)
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.department == dept })
    }

    @Test
    fun filterKudos_byHashtag_keepsOnlyMatchingAndDropsOthers() {
        val all = KudosRepository.highlightKudos
        val result = KudosViewModel.filterKudos(all, department = null, hashtag = "#Inspiring")
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { "#Inspiring" in it.hashtags })
        assertEquals(all.count { "#Inspiring" in it.hashtags }, result.size)
    }

    @Test
    fun filterKudos_byBoth_appliesAnd() {
        val all = KudosRepository.highlightKudos
        val target = all.first { "#Dedicated" in it.hashtags }
        val result = KudosViewModel.filterKudos(all, department = target.department, hashtag = "#Dedicated")
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.department == target.department && "#Dedicated" in it.hashtags })
    }

    @Test
    fun allKudos_isPopulatedWithUniqueIds() {
        val all = KudosRepository.allKudos
        assertTrue(all.isNotEmpty())
        assertEquals(all.size, all.map { it.id }.toSet().size)
    }

    @Test
    fun allKudos_likeToggle_adjustsOnlyTargetCard() {
        val seed = KudosRepository.allKudos
        val target = seed.first()
        val result = KudosViewModel.toggleLikeIn(seed, target.id)
        val liked = result.first { it.id == target.id }
        assertTrue(liked.liked)
        assertEquals(target.hearts + 1, liked.hearts)
        assertEquals(seed.drop(1), result.drop(1))
    }
}
