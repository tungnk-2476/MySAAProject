package com.example.mysaaproject

import com.example.mysaaproject.data.kudos.HeroLevel
import com.example.mysaaproject.data.profile.ProfileRepository
import com.example.mysaaproject.ui.kudos.KudosViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Verifies the Profile mock data ("[iOS] Profile") and the shared heart like-toggle on profile lists. */
class ProfileViewModelTest {

    @Test
    fun ownProfile_hasLegendHeroAndSixLockedBadges() {
        val own = ProfileRepository.ownProfile
        assertEquals(HeroLevel.LEGEND, own.hero)
        assertEquals(6, own.badges.size)
        assertTrue(own.badges.none { it.unlocked })
    }

    @Test
    fun ownProfile_statsMatchDesignLiterals() {
        val stats = ProfileRepository.ownProfile.stats
        assertEquals(5, stats.received)
        assertEquals(25, stats.sent)
        assertEquals(25, stats.hearts)
        assertEquals(25, stats.secretBoxOpened)
        assertEquals(25, stats.secretBoxUnopened)
    }

    @Test
    fun otherProfile_carriesIdentityAndSixUnlockedNamedBadges() {
        val other = ProfileRepository.otherProfile("Đỗ Hoàng Hiệp", "OPD")
        assertEquals("Đỗ Hoàng Hiệp", other.name)
        assertEquals("OPD", other.code)
        assertEquals(HeroLevel.RISING, other.hero)
        assertEquals(6, other.badges.size)
        assertTrue(other.badges.all { it.unlocked && it.name.isNotEmpty() })
        assertEquals("REVIVAL", other.badges.first().name)
    }

    @Test
    fun receivedAndSentLists_arePopulatedWithUniqueIds() {
        listOf(
            ProfileRepository.receivedKudos,
            ProfileRepository.sentKudos,
            ProfileRepository.otherReceivedKudos,
        ).forEach { list ->
            assertTrue(list.isNotEmpty())
            assertEquals(list.size, list.map { it.id }.toSet().size)
        }
    }

    @Test
    fun toggleLike_onProfileList_adjustsOnlyTargetCard() {
        val seed = ProfileRepository.receivedKudos
        val target = seed.first()
        assertFalse(target.liked)

        val result = KudosViewModel.toggleLikeIn(seed, target.id)
        val liked = result.first { it.id == target.id }
        assertTrue(liked.liked)
        assertEquals(target.hearts + 1, liked.hearts)
        assertEquals(seed.drop(1), result.drop(1))
    }
}
