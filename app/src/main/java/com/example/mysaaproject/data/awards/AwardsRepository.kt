package com.example.mysaaproject.data.awards

import kotlinx.coroutines.delay

/**
 * Stubbed awards data source. No backend exists yet, so this returns mock award data
 * extracted from the MoMorph design after a short simulated latency (so the Home screen's
 * Loading state is observable). Returns [Result] so the ViewModel can model Success/Error
 * and drive the Retry flow (TC_IOS_HOME_FUN_003).
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
                name = "Top Talent",
                description = "Giải thưởng Top Talent vinh danh những cá nhân xuất sắc trên mọi phương diện.",
            ),
            Award(
                id = "top_project",
                name = "Top Project",
                description = "Giải thưởng Top Project vinh danh các tập thể dự án xuất sắc nhất năm.",
            ),
            Award(
                id = "top_leader",
                name = "Top Project Leader",
                description = "Vinh danh những người dẫn dắt dự án xuất sắc và truyền cảm hứng cho đồng đội.",
            ),
        )
    }
}
