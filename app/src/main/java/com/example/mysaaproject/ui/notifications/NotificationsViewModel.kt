package com.example.mysaaproject.ui.notifications

import androidx.lifecycle.ViewModel
import com.example.mysaaproject.data.notifications.NotificationItem
import com.example.mysaaproject.data.notifications.NotificationsRepository
import kotlinx.coroutines.flow.StateFlow

/**
 * Exposes the shared notification list and read-state mutations. State lives in the shared
 * [NotificationsRepository] so the Home bell badge reacts to the same changes.
 */
class NotificationsViewModel : ViewModel() {

    val items: StateFlow<List<NotificationItem>> = NotificationsRepository.items

    fun markRead(id: String) = NotificationsRepository.markRead(id)

    fun markAllRead() = NotificationsRepository.markAllRead()
}
