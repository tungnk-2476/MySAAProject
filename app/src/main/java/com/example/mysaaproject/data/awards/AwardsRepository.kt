package com.example.mysaaproject.data.awards

import com.example.mysaaproject.R
import kotlinx.coroutines.delay

/**
 * Stubbed awards data source. No backend exists yet, so this returns mock award data
 * extracted from the MoMorph design after a short simulated latency (so the Home screen's
 * Loading state is observable). Returns [Result] so the ViewModel can model Success/Error
 * and drive the Retry flow (TC_IOS_HOME_FUN_003). Text is held as string-resource ids so awards
 * switch with the app language.
 */
class AwardsRepository {

    suspend fun loadAwards(): Result<List<Award>> {
        delay(SIMULATED_LATENCY_MS)
        return Result.success(MOCK_AWARDS)
    }

    companion object {
        private const val SIMULATED_LATENCY_MS = 600L

        val MOCK_AWARDS = listOf(
            Award(
                id = "top_talent",
                name = R.string.award_top_talent_name,
                description = R.string.award_top_talent_desc,
                longDescription = R.string.award_top_talent_long,
                quantity = 10,
                quantityUnit = R.string.award_unit_individual,
                prizeValue = R.string.award_prize_talent,
            ),
            Award(
                id = "top_project",
                name = R.string.award_top_project_name,
                description = R.string.award_top_project_desc,
                longDescription = R.string.award_top_project_long,
                quantity = 5,
                quantityUnit = R.string.award_unit_project,
                prizeValue = R.string.award_prize_project,
            ),
            Award(
                id = "top_leader",
                name = R.string.award_top_leader_name,
                description = R.string.award_top_leader_desc,
                longDescription = R.string.award_top_leader_long,
                quantity = 3,
                quantityUnit = R.string.award_unit_individual,
                prizeValue = R.string.award_prize_leader,
            ),
        )
    }
}
