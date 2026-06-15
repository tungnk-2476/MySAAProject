package com.example.mysaaproject.data.profile

import com.example.mysaaproject.data.kudos.HeroLevel
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.data.kudos.KudosStats

/**
 * Stub Profile data — no backend yet. Mock content extracted from the MoMorph "[iOS] Profile bản
 * thân" / "[iOS] Profile người khác" designs (identity, icon collection, stats, received/sent kudos).
 */
object ProfileRepository {

    /** The six icon-collection badge titles shown on another member's profile (design literals). */
    private val badgeTitles = listOf(
        "REVIVAL", "TOUCH OF LIGHT", "STAY GOLD",
        "FLOW TO HORIZON", "BEYOND THE BOUNDARY", "ROOT FUTHER",
    )

    /** Logged-in user (own profile): "Legend Hero", six not-yet-earned (locked) badge slots. */
    val ownProfile = UserProfile(
        name = "Huỳnh Dương Xuân Nhật",
        code = "CEVC3",
        hero = HeroLevel.LEGEND,
        badges = List(6) { ProfileBadge(id = "own-$it", name = "", unlocked = false) },
        stats = KudosStats(
            received = 5,
            sent = 25,
            hearts = 25,
            secretBoxOpened = 25,
            secretBoxUnopened = 25,
            fireBonusActive = false,
        ),
    )

    /**
     * Build another member's profile from the (name, code) tapped on a kudo card. Their six badges
     * are all unlocked with titles (per the "Profile người khác" design). Stats are unused (the
     * other-profile screen shows a "thank-you" CTA instead of the stats block).
     */
    fun otherProfile(name: String, code: String): UserProfile = UserProfile(
        name = name,
        code = code,
        hero = HeroLevel.RISING,
        badges = badgeTitles.mapIndexed { i, title -> ProfileBadge(id = "other-$i", name = title, unlocked = true) },
        stats = ownProfile.stats,
    )

    /** Kudos the user has received vs. sent (own-profile "Đã nhận / Đã gửi" filter). Mock, reused from the feed. */
    val receivedKudos: List<Kudo> = KudosRepository.allKudos.take(5)
    val sentKudos: List<Kudo> = KudosRepository.allKudos.takeLast(5)

    /** Kudos shown on another member's profile (received). */
    val otherReceivedKudos: List<Kudo> = KudosRepository.allKudos.take(5)
}
