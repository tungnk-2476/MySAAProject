package com.example.mysaaproject.data.profile

import com.example.mysaaproject.data.kudos.HeroLevel
import com.example.mysaaproject.data.kudos.KudosStats

/**
 * A single icon-collection badge ("Bộ sưu tập icon"). A [unlocked] badge shows its art + [name];
 * a locked one renders as an empty dark slot (own profile — none earned yet, per the design).
 */
data class ProfileBadge(
    val id: String,
    val name: String,
    val unlocked: Boolean,
)

/**
 * A Sunner's profile shown on the Profile screen — the logged-in user (own) or another member.
 * Mock data extracted from the MoMorph "[iOS] Profile" designs (no backend yet, like sibling screens).
 */
data class UserProfile(
    val name: String,
    val code: String,
    val hero: HeroLevel,
    val badges: List<ProfileBadge>,
    val stats: KudosStats,
)
