package com.example.mysaaproject.data.awards

/**
 * A single SAA 2025 award category. [name]/[description] back the Home cards; the remaining fields back
 * the Award Detail screen ([longDescription], [quantity]/[quantityUnit], [prizeValue]). Mock content from
 * the MoMorph designs. Detail fields default so existing Home usage is unaffected.
 */
data class Award(
    val id: String,
    val name: String,
    val description: String,
    val longDescription: String = description,
    val quantity: Int = 0,
    val quantityUnit: String = "",
    val prizeValue: String = "",
)
