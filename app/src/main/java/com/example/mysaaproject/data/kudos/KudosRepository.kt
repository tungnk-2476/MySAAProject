package com.example.mysaaproject.data.kudos

/**
 * Stub Kudos data — no backend yet. Mock content extracted from the MoMorph "[iOS] Sun*Kudos"
 * design (highlight carousel, all-kudos feed, personal stats, top-10 recipients, spotlight board).
 */
object KudosRepository {

    /** Department options for the Highlight "Phòng ban" filter (literal design values). */
    val departments = listOf("CEVC2", "CEVC3", "CEVC4", "CEVC1", "OPD", "Infra")

    /** Hashtag options for the Highlight "Hashtag" filter (design values, deduped). */
    val hashtagOptions = listOf("#Dedicated", "#Inspiring")

    val highlightKudos: List<Kudo> = List(5) { i -> sampleKudo("h$i", i) }
    val feedKudos: List<Kudo> = List(4) { i -> sampleKudo("f$i", i) }

    /** Full Kudos feed shown on the dedicated "All Kudos" screen. */
    val allKudos: List<Kudo> = List(8) { i -> sampleKudo("a$i", i) }

    /** Look up a single kudo by id across every mock list (for the View Kudo detail screen). */
    fun findById(id: String): Kudo? =
        (highlightKudos + feedKudos + allKudos).firstOrNull { it.id == id }

    // --- New Kudo (Send Kudo) form options ---

    /** Recent Sunner searches shown on the Search Sunner screen (reuses [Recipient]; own id namespace). */
    val recentSearches = listOf(
        Recipient("rs1", "Dương Huỳnh Xuân Nhật", "CECV1"),
        Recipient("rs2", "Dương Huỳnh Xuân Nhật", "CECV1"),
    )

    /** Recipient search results for the "Người nhận" dropdown. */
    val kudoRecipients = listOf(
        Recipient("u1", "Dương Huỳnh Xuân Nhật", "CECV1"),
        Recipient("u2", "Dương Huỳnh Xuân Nhân", "CECV1"),
        Recipient("u3", "Huỳnh Dương Xuân Nhật", "CECV10"),
        Recipient("u4", "Đỗ Hoàng Hiệp", "OPD"),
        Recipient("u5", "Mai Phương Thúy", "Infra"),
    )

    /** Award titles (danh hiệu) for the New Kudo title selector. */
    val titleOptions = listOf(
        "NGƯỜI HÙNG CỦA LÒNG EM",
        "IDOL GIỚI TRẺ",
        "IDOL THẦM LẶNG",
        "CHIẾN BINH QUẢ CẢM",
        "NGÔI SAO HY VỌNG",
    )

    /** Hashtag options for the New Kudo hashtag dropdown (literal design values). */
    val sendHashtagOptions = listOf(
        "#High-perorming", "#BE PROFESSIONAL", "#BE OPTIMISTIC", "#BE A TEAM",
        "#THINK OUTSIDE THE BOX", "#GET RISKY", "#GO FAST", "#WASSHOI",
    )

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

    private fun sampleKudo(id: String, index: Int): Kudo {
        // Vary tag membership so the Hashtag filter visibly narrows the carousel; most cards keep a mixed row.
        val tags = when (index % 5) {
            2 -> listOf("#Inspiring")
            4 -> listOf("#Dedicated")
            else -> listOf("#Dedicated", "#Inspiring")
        }
        return Kudo(
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
            hashtags = List(6) { i -> tags[i % tags.size] },
            hearts = 1000 + index,
            liked = false,
            department = departments[index % departments.size],
            imageCount = 5,
        )
    }
}
