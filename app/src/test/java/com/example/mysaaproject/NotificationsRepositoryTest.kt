package com.example.mysaaproject

import com.example.mysaaproject.data.notifications.NotificationsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** Verifies the shared unread-count logic that drives the Home bell badge (TC_NOTIF_FUN_001/002). */
class NotificationsRepositoryTest {

    private fun unread() = NotificationsRepository.items.value.count { !it.isRead }

    @Before
    fun reset() = NotificationsRepository.reset()

    @Test
    fun seed_hasUnreadNotifications() {
        assertTrue(unread() >= 1)
    }

    @Test
    fun markRead_decrementsUnreadCount() {
        val target = NotificationsRepository.items.value.first { !it.isRead }
        val before = unread()
        NotificationsRepository.markRead(target.id)
        assertEquals(before - 1, unread())
    }

    @Test
    fun markAllRead_clearsAllUnread() {
        NotificationsRepository.markAllRead()
        assertEquals(0, unread())
        assertTrue(NotificationsRepository.items.value.all { it.isRead })
    }

    @Test
    fun markRead_unknownId_isNoOp() {
        val before = unread()
        NotificationsRepository.markRead("does-not-exist")
        assertEquals(before, unread())
    }
}
