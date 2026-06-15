package com.example.mysaaproject

import com.example.mysaaproject.ui.home.SevenSegment
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Verifies the pure 7-segment digit map used by the Home LED countdown. */
class SevenSegmentTest {

    @Test
    fun everyDigit_hasSevenSegments() {
        (0..9).forEach { d -> assertEquals("digit $d", 7, SevenSegment.segments(d).size) }
    }

    @Test
    fun eight_lightsAllSegments() {
        assertTrue(SevenSegment.segments(8).all { it })
    }

    @Test
    fun one_lightsOnlyTopRightAndBottomRight() {
        // order: a, b, c, d, e, f, g
        assertArrayEquals(
            booleanArrayOf(false, true, true, false, false, false, false),
            SevenSegment.segments(1),
        )
    }

    @Test
    fun zero_lightsAllButMiddle() {
        assertArrayEquals(
            booleanArrayOf(true, true, true, true, true, true, false),
            SevenSegment.segments(0),
        )
    }

    @Test
    fun outOfRangeDigit_isClampedNotCrashing() {
        assertArrayEquals(SevenSegment.segments(9), SevenSegment.segments(42))
        assertArrayEquals(SevenSegment.segments(0), SevenSegment.segments(-5))
    }
}
