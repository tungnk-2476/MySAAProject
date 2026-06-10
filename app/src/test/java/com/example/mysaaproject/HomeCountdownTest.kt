package com.example.mysaaproject

import com.example.mysaaproject.ui.home.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Verifies the pure countdown computation, including the past-event edge case (TC_IOS_HOME_FUN_002). */
class HomeCountdownTest {

    @Test
    fun pastEvent_isAllZeroAndNotBeforeEvent() {
        val c = HomeViewModel.computeCountdown(-10_000L)
        assertEquals(0, c.days)
        assertEquals(0, c.hours)
        assertEquals(0, c.minutes)
        assertFalse(c.isBeforeEvent)
    }

    @Test
    fun exactlyZero_isNotBeforeEvent() {
        assertFalse(HomeViewModel.computeCountdown(0L).isBeforeEvent)
    }

    @Test
    fun futureEvent_splitsDaysHoursMinutes() {
        val remaining = ((2L * 24 + 3) * 60 + 4) * 60_000L // 2d 3h 4m
        val c = HomeViewModel.computeCountdown(remaining)
        assertEquals(2, c.days)
        assertEquals(3, c.hours)
        assertEquals(4, c.minutes)
        assertTrue(c.isBeforeEvent)
    }

    @Test
    fun secondsBelowAMinute_floorToZeroMinutesButStillUpcoming() {
        val c = HomeViewModel.computeCountdown(30_000L) // 30s
        assertEquals(0, c.days)
        assertEquals(0, c.hours)
        assertEquals(0, c.minutes)
        assertTrue(c.isBeforeEvent)
    }
}
