package com.example.mysaaproject.data.awards

import androidx.annotation.StringRes

/**
 * A single SAA 2025 award category. [name]/[description] back the Home cards; the remaining fields back
 * the Award Detail screen ([longDescription], [quantity]/[quantityUnit], [prizeValue]). Localizable text
 * is held as string-resource ids (resolved via `appString` at render) so awards switch with the app
 * language. Mock content from the MoMorph designs.
 */
data class Award(
    val id: String,
    @param:StringRes val name: Int,
    @param:StringRes val description: Int,
    @param:StringRes val longDescription: Int = description,
    val quantity: Int = 0,
    @param:StringRes val quantityUnit: Int = 0,
    @param:StringRes val prizeValue: Int = 0,
)
