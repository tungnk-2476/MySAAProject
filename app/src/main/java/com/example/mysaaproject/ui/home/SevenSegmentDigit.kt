package com.example.mysaaproject.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

/**
 * Pure 7-segment lookup (unit-testable): maps a digit 0–9 to its lit segments in the order
 * [a, b, c, d, e, f, g] (a=top, b=top-right, c=bottom-right, d=bottom, e=bottom-left, f=top-left,
 * g=middle).
 */
object SevenSegment {
    private val MAP = arrayOf(
        booleanArrayOf(true, true, true, true, true, true, false),    // 0
        booleanArrayOf(false, true, true, false, false, false, false), // 1
        booleanArrayOf(true, true, false, true, true, false, true),    // 2
        booleanArrayOf(true, true, true, true, false, false, true),    // 3
        booleanArrayOf(false, true, true, false, false, true, true),   // 4
        booleanArrayOf(true, false, true, true, false, true, true),    // 5
        booleanArrayOf(true, false, true, true, true, true, true),     // 6
        booleanArrayOf(true, true, true, false, false, false, false),  // 7
        booleanArrayOf(true, true, true, true, true, true, true),      // 8
        booleanArrayOf(true, true, true, true, false, true, true),     // 9
    )

    fun segments(digit: Int): BooleanArray = MAP[digit.coerceIn(0, 9)]
}

/**
 * A single LED/LCD-style 7-segment digit drawn on a Canvas (no font dependency). Lit segments are
 * bright; unlit ones stay as faint "ghost" strokes, matching the Home countdown design's display look.
 */
@Composable
fun SevenSegmentDigit(
    digit: Int,
    modifier: Modifier = Modifier,
    litColor: Color = Color.White,
    unlitColor: Color = Color.White.copy(alpha = 0.07f),
) {
    val on = SevenSegment.segments(digit)
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val t = w * 0.16f          // segment thickness
        val gap = t * 0.55f        // gap between adjacent segments
        val left = t
        val right = w - t
        val top = t
        val midY = h / 2f
        val bottom = h - t

        fun seg(lit: Boolean, x1: Float, y1: Float, x2: Float, y2: Float) {
            drawLine(
                color = if (lit) litColor else unlitColor,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = t,
                cap = StrokeCap.Round,
            )
        }

        seg(on[0], left + gap, top, right - gap, top)        // a — top
        seg(on[1], right, top + gap, right, midY - gap)      // b — top-right
        seg(on[2], right, midY + gap, right, bottom - gap)   // c — bottom-right
        seg(on[3], left + gap, bottom, right - gap, bottom)  // d — bottom
        seg(on[4], left, midY + gap, left, bottom - gap)     // e — bottom-left
        seg(on[5], left, top + gap, left, midY - gap)        // f — top-left
        seg(on[6], left + gap, midY, right - gap, midY)      // g — middle
    }
}
