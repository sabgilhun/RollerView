package com.sabgil.roller.frames

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.sabgil.roller.helper.coerceLimit
import com.sabgil.roller.helper.coerceOrInset
import com.sabgil.roller.helper.findFirstItemIndex
import com.sabgil.roller.helper.findLastItemIndex
import com.sabgil.roller.models.CircularLane
import com.sabgil.roller.models.Orientation

class FlatHorizontalFocusFrame(
    orientation: Orientation,
    private val definedWidth: Int,
    private val definedHeight: Int,
    private val circularLane: CircularLane,
    private val paint: Paint?
) : FocusFrame {

    private val orientation: Int = when (orientation) {
        Orientation.LEFT -> 1
        Orientation.RIGHT -> -1
        else -> throw IllegalStateException(
            "Orientation value of vertical frame must not be UP or DOWN"
        )
    }

    private var viewWidth = 0
    private var viewHeight = 0

    private var rect: Rect = Rect()
    private var leadingSpace = 0
    private var trailingSpace = 0
    private var firstItemBoundary = 0
    private var lastItemBoundary = 0

    override fun resize(viewWidth: Int, viewHeight: Int) {
        if (this.viewHeight != viewHeight || this.viewWidth != viewWidth) {
            this.viewHeight = viewHeight
            this.viewWidth = viewWidth
            updatePosition()
            updateCircularLane()
        }
    }

    override fun draw(canvas: Canvas, progress: Float) {
        val position = calCurFocusPosition(progress, circularLane)
        val displayItemRange = calcDisplayItemRange(position)

        for (i in displayItemRange) {
            val diff = (calcItemStartPos(i) - position) * orientation
            val drawable = circularLane.getItem(i)
            drawable.bounds = Rect(rect).apply { offset(diff, 0) }
            drawable.draw(canvas)
        }

        paint?.let { canvas.drawRect(rect, it) }
    }

    private fun updatePosition() {
        val (start, end) = coerceLimit(viewWidth, definedWidth)
        val (top, bottom) = coerceOrInset(viewHeight, definedHeight)
        val width = end - start
        calcExtraSpace(width)
        calcBoundary(width)
        calcRect(start, end, top, bottom)
    }

    private fun calcRect(start: Int, end: Int, top: Int, bottom: Int) {
        rect = Rect(start + leadingSpace, top, end + leadingSpace, bottom)
    }

    private fun calcExtraSpace(width: Int) {
        leadingSpace = (viewWidth - width) / 2
        trailingSpace = (viewWidth + width) / 2
    }

    private fun calcBoundary(width: Int) {
        firstItemBoundary = leadingSpace + width
        lastItemBoundary = trailingSpace + width
    }

    private fun updateCircularLane() {
        circularLane.resize(rect.width())
    }

    private fun calCurFocusPosition(progress: Float, circularLane: CircularLane): Int =
        (progress * circularLane.totalLen).toInt()

    private fun calcDisplayItemRange(progress: Int): IntRange {
        val first = findFirstItemIndex(
            progress - firstItemBoundary,
            this::calcItemStartPos
        )

        val last = findLastItemIndex(
            first,
            progress + lastItemBoundary,
            this::calcItemStartPos
        )

        return first..last
    }

    private fun calcItemStartPos(index: Int) = index * rect.width()
}