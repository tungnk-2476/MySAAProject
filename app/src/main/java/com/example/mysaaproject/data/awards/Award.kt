package com.example.mysaaproject.data.awards

/**
 * A single SAA 2025 award category shown as a card on the Home screen.
 * [name] and [description] come from the MoMorph "[iOS] Home" design (mock content).
 */
data class Award(
    val id: String,
    val name: String,
    val description: String,
)
