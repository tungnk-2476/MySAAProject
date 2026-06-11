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
                longDescription = "Giải thưởng Top Talent vinh danh những cá nhân xuất sắc toàn diện – những " +
                    "người không ngừng khẳng định năng lực chuyên môn vững vàng, hiệu suất công việc vượt trội, " +
                    "luôn mang lại giá trị vượt kỳ vọng, được đánh giá cao bởi khách hàng và đồng đội. Với tinh " +
                    "thần sẵn sàng nhận mọi nhiệm vụ tổ chức giao phó, họ luôn là nguồn cảm hứng, thúc đẩy động " +
                    "lực và tạo ảnh hưởng tích cực đến cả tập thể.",
                quantity = 10,
                quantityUnit = "Cá nhân",
                prizeValue = "7.000.000 VNĐ",
            ),
            Award(
                id = "top_project",
                name = "Top Project",
                description = "Giải thưởng Top Project vinh danh các tập thể dự án xuất sắc nhất năm.",
                longDescription = "Giải thưởng Top Project vinh danh các tập thể dự án xuất sắc nhất năm – " +
                    "những đội ngũ tạo ra sản phẩm chất lượng, hợp tác hiệu quả và mang lại giá trị vượt trội cho " +
                    "khách hàng cũng như tổ chức.",
                quantity = 5,
                quantityUnit = "Dự án",
                prizeValue = "20.000.000 VNĐ",
            ),
            Award(
                id = "top_leader",
                name = "Top Project Leader",
                description = "Vinh danh những người dẫn dắt dự án xuất sắc và truyền cảm hứng cho đồng đội.",
                longDescription = "Vinh danh những người dẫn dắt dự án xuất sắc – những leader truyền cảm hứng, " +
                    "định hướng rõ ràng và đưa đội ngũ vượt qua thử thách để đạt kết quả ấn tượng.",
                quantity = 3,
                quantityUnit = "Cá nhân",
                prizeValue = "10.000.000 VNĐ",
            ),
        )
    }
}
