package com.example.mysaaproject.data.kudos

import androidx.annotation.StringRes

/** Hero recognition level shown as a badge pill on a kudo card (brand terms — not localized). */
enum class HeroLevel(val label: String) {
    RISING("Rising Hero"),
    LEGEND("Legend Hero"),
}

/**
 * A single Kudos post. Names/codes/time/hashtags are language-neutral mock data; the [title] and
 * [content] are held as string-resource ids (resolved via `appString` at render) so the sample
 * content switches with the app language.
 */
data class Kudo(
    val id: String,
    val senderName: String,
    val senderCode: String,
    val senderHero: HeroLevel,
    val receiverName: String,
    val receiverCode: String,
    val receiverHero: HeroLevel,
    val time: String,
    @param:StringRes val title: Int,
    @param:StringRes val content: Int,
    val hashtags: List<String>,
    val hearts: Int,
    /** Department the kudo belongs to (used by the Highlight "Phòng ban" filter). */
    val department: String,
    val liked: Boolean = false,
    /** Number of attached images (rendered as placeholder tiles on the View Kudo detail screen). */
    val imageCount: Int = 0,
    /**
     * Free-text body for user-composed kudos. When set (non-blank) it is rendered instead of the
     * localized [content] resource — mock/sample kudos leave this null and use [content].
     */
    val contentText: String? = null,
)

/** Single like rule (the one source of truth): flip [Kudo.liked] and adjust hearts by one. */
fun Kudo.toggleLiked(): Kudo = copy(liked = !liked, hearts = hearts + if (liked) -1 else 1)

/** Personal Kudos statistics block (All Kudos section). */
data class KudosStats(
    val received: Int,
    val sent: Int,
    val hearts: Int,
    val secretBoxOpened: Int,
    val secretBoxUnopened: Int,
    val fireBonusActive: Boolean,
)

/** A recipient in the "latest 10 gift recipients" list. [message] is a localizable string-resource id. */
data class GiftRecipient(
    val id: String,
    val name: String,
    @param:StringRes val message: Int,
)
