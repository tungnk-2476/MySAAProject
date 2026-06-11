package com.example.mysaaproject.data.kudos

/** Hero recognition level shown as a badge pill on a kudo card (brand terms — not localized). */
enum class HeroLevel(val label: String) {
    RISING("Rising Hero"),
    LEGEND("Legend Hero"),
}

/** A single Kudos post. Content/names are mock data from the MoMorph design. */
data class Kudo(
    val id: String,
    val senderName: String,
    val senderCode: String,
    val senderHero: HeroLevel,
    val receiverName: String,
    val receiverCode: String,
    val receiverHero: HeroLevel,
    val time: String,
    val title: String,
    val content: String,
    val hashtags: List<String>,
    val hearts: Int,
    /** Department the kudo belongs to (used by the Highlight "Phòng ban" filter). */
    val department: String,
    val liked: Boolean = false,
    /** Number of attached images (rendered as placeholder tiles on the View Kudo detail screen). */
    val imageCount: Int = 0,
)

/** Personal Kudos statistics block (All Kudos section). */
data class KudosStats(
    val received: Int,
    val sent: Int,
    val hearts: Int,
    val secretBoxOpened: Int,
    val secretBoxUnopened: Int,
    val fireBonusActive: Boolean,
)

/** A recipient in the "latest 10 gift recipients" list. */
data class GiftRecipient(
    val id: String,
    val name: String,
    val message: String,
)
