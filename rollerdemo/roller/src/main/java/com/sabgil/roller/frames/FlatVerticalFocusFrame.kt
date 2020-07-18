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

class FlatVerticalFocusFrame(
    orientation: Orientation,
    private val definedWidth: Int,
    private val definedHeight: Int,
    private val circularLane: CircularLane,
    private val paint: Paint?
) : FocusFrame {

    private val orientation: Int = when (orientation) {
        Orientation.UP -> 1
        Orientation.DOWN -> -1
        else -> throw IllegalStateException(
            "Orientation value of vertical frame must not be LEFT or RIGHT"
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
            val diff = (calcItemTopPos(i) - position) * orientation
            val drawable = circularLane.getItem(i)
            drawable.bounds = Rect(rect).apply { offset(0, diff) }
            drawable.draw(canvas)
        }

        paint?.let { canvas.drawRect(rect, it) }
    }

    private fun updatePosition() {
        val (start, end) = coerceOrInset(viewWidth, definedWidth)
        val (top, bottom) = coerceLimit(viewHeight, definedHeight)
        val height = bottom - top
        calcExtraSpace(height)
        calcBoundary(height)
        calcRect(start, end, top, bottom)
    }

    private fun calcRect(start: Int, end: Int, top: Int, bottom: Int) {
        rect = Rect(start, top + leadingSpace, end, bottom + leadingSpace)
    }

    private fun calcExtraSpace(height: Int) {
        leadingSpace = (viewHeight - height) / 2
        trailingSpace = (viewHeight + height) / 2
    }

    private fun calcBoundary(height: Int) {
        firstItemBoundary = leadingSpace + height
        lastItemBoundary = trailingSpace + height
    }

    private fun updateCircularLane() {
        circularLane.resize(rect.height())
    }

    private fun calCurFocusPosition(progress: Float, circularLane: CircularLane): Int =
        (progress * circularLane.totalLen).toInt()

    private fun calcDisplayItemRange(progress: Int): IntRange {
        val first = findFirstItemIndex(
            progress - firstItemBoundary,
            this::calcItemTopPos
        )

        val last = findLastItemIndex(
            first,
            progress + lastItemBoundary,
            this::calcItemTopPos
        )

        return first..last
    }

    private fun calcItemTopPos(index: Int) = index * rect.height()
}