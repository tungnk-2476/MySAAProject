package com.example.mysaaproject.data.kudos

/**
 * Stub Kudos data — no backend yet. Mock content extracted from the MoMorph "[iOS] Sun*Kudos"
 * design (highlight carousel, all-kudos feed, personal stats, top-10 recipients, spotlight board).
 */
object KudosRepository {

    val highlightKudos: List<Kudo> = List(5) { i -> sampleKudo("h$i", i) }
    val feedKudos: List<Kudo> = List(4) { i -> sampleKudo("f$i", i) }

    val stats = KudosStats(
        received = 25,
        sent = 25,
        hearts = 25,
        secretBoxOpened = 25,
        secretBoxUnopened = 25,
        fireBonusActive = true,
    )

    val recipients: List<GiftRecipient> = List(3) { i ->
        GiftRecipient("r$i", "Huỳnh Dương Xuân", "Nhận được 1 áo phông SAA")
    }

    val spotlightCount = 388
    val spotlightNames = listOf(
        "Đỗ Hoàng Hiệp", "Dương Thúy An", "Mai Phương Thúy", "Lê Kiều Trang",
        "Nguyễn Văn Quy", "Nguyễn Bá Chức", "Nguyễn Hoàng Linh",
    )

    private fun sampleKudo(id: String, index: Int): Kudo = Kudo(
        id = id,
        senderName = "Huỳnh Dương Xuân Nhật",
        senderCode = "CECV10",
        senderHero = HeroLevel.RISING,
        receiverName = "Dương Xuân Huỳnh Nhật",
        receiverCode = "CECV10",
        receiverHero = HeroLevel.LEGEND,
        time = "10:00 - 10/30/2025",
        title = "IDOL GIỚI TRẺ",
        content = "Cảm ơn người em bình thường nhưng phi thường :D Cảm ơn sự chăm chỉ, " +
            "cần mẫn của em đã tạo động lực rất lớn cho cả team trong suốt thời gian qua. " +
            "Chúc em luôn giữ vững tinh thần này nhé!",
        hashtags = listOf("#Dedicated", "#Inspiring", "#Dedicated", "#Inspiring", "#Dedicated", "#Inspiring"),
        hearts = 1000 + index,
        liked = false,
    )
}
