package com.example.mysaaproject.data.notifications

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * In-memory singleton holding the notification list and read-state. Shared by the Notifications
 * screen and the Home bell badge, so marking items read updates both live (TC_NOTIF_FUN_001/002).
 * No backend yet — seeded with the seven mock notifications from the design.
 */
object NotificationsRepository {

    private val _items = MutableStateFlow(seed())
    val items: StateFlow<List<NotificationItem>> = _items.asStateFlow()

    fun markRead(id: String) {
        _items.update { list -> list.map { if (it.id == id) it.copy(isRead = true) else it } }
    }

    fun markAllRead() {
        _items.update { list -> list.map { it.copy(isRead = true) } }
    }

    /** Resets to the initial seed — used by tests to isolate runs against this shared singleton. */
    fun reset() {
        _items.value = seed()
    }

    private fun seed(): List<NotificationItem> = listOf(
        NotificationItem(
            id = "n1",
            type = NotificationType.KUDOS_RECEIVED,
            message = "Sunner Huỳnh Dương Xuân Nhật vừa gửi đến bạn lời ghi nhận đầy yêu thương!",
            relativeTime = "15 phút trước",
            isRead = false,
        ),
        NotificationItem(
            id = "n2",
            type = NotificationType.HEART_RECEIVED,
            message = "Wow! Lời nhắn gửi của bạn cho Sunner <tên Sunner> vừa nhận thêm lượt tim!",
            relativeTime = "1 giờ trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n3",
            type = NotificationType.SECRET_BOX,
            message = "Chúc mừng! Bạn vừa nhận được lượt mở Secret Box mới! Click vào đây để mở ngay nhé!",
            relativeTime = "1 ngày trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n4",
            type = NotificationType.LEVEL_UP,
            message = "Bạn nhận được <X> lời nhắn gửi từ đồng nghiệp và thăng hạng <tên level>! Tiếp tục lan tỏa năng lượng tích cực đến đồng nghiệp nhé!",
            relativeTime = "1 ngày trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n5",
            type = NotificationType.CONTENT_HIDDEN,
            message = "Tiếc quá! Bạn có một lời nhắn bị tạm ẩn vì \"vướng\" một số tiêu chuẩn! Hãy xem các tiêu chuẩn và gửi lại cho đồng đội nhé!",
            relativeTime = "1 tháng trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n6",
            type = NotificationType.BADGE_COLLECTED,
            message = "Chúc mừng bạn đã thu thập đủ 6 huy hiệu của SAA. Bạn đã nhận được phần quà từ BTC chính là <X>. BTC sẽ liên hệ để gửi quà đến bạn vào cuối sự kiện.",
            relativeTime = "1 tháng trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n7",
            type = NotificationType.REVIEW_REQUEST,
            message = "\"Có <x> lời nhắn cần bạn xem xét!\" Một lời nhắn vừa bị hệ thống gắn cờ nghi ngờ vi phạm tiêu chuẩn. Vui lòng kiểm tra và xác nhận trạng thái: Hợp lệ / Tạm ẩn / Reject.",
            relativeTime = "1 tháng trước",
            isRead = true,
        ),
    )
}
