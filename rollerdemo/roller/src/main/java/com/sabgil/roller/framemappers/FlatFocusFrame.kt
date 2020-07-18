package com.sabgil.roller.framemappers

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.sabgil.roller.models.CircularLane

class FlatFocusFrame(
    private val definedWidth: Int,
    private val definedHeight: Int,
    private val circularLane: CircularLane,
    private val paint: Paint?
) : FocusFrame {

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
            val diff = calcItemTopPos(i) - position
            val drawable = circularLane.getItem(i)
            drawable.bounds = Rect(rect).apply { offset(0, diff) }
            drawable.draw(canvas)
        }

        paint?.let { canvas.drawRect(rect, it) }
    }

    private fun updatePosition() {
        val (start, end) = coerceWidth(viewWidth, definedWidth)
        val (top, bottom) = coerceHeight(viewHeight, definedHeight)
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

    private fun coerceWidth(limit: Int, actual: Int): Pair<Int, Int> =
        if (limit <= actual) {
            0 to limit
        } else {
            val paddingStart = (limit - actual) / 2
            val paddingEnd = limit - actual - paddingStart
            paddingStart to limit - paddingEnd
        }

    private fun coerceHeight(limit: Int, actual: Int): Pair<Int, Int> =
        if (limit <= actual) {
            0 to limit
        } else {
            0 to actual
        }

    private fun calCurFocusPosition(progress: Float, circularLane: CircularLane): Int =
        (progress * circularLane.totalLen).toInt()

    private fun calcDisplayItemRange(progress: Int): IntRange {
        val first = findFirstItemIndex(progress - firstItemBoundary)
        val last = findLastItemIndex(first, progress + lastItemBoundary)
        return first..last
    }

    private fun findFirstItemIndex(diff: Int): Int {
        fun checkPreviousItem(): Int {
            var innerIndex = -1
            while (calcItemTopPos(innerIndex) > diff) {
                innerIndex--
            }
            return innerIndex + 1
        }

        fun checkNextItem(): Int {
            var innerIndex = 1
            while (calcItemTopPos(innerIndex) < diff) {
                innerIndex++
            }
            return innerIndex
        }

        return if (calcItemTopPos(0) > diff) {
            checkPreviousItem()
        } else {
            checkNextItem()
        }
    }

    private fun findLastItemIndex(initIndex: Int, diff: Int): Int {
        var innerIndex = initIndex
        while (calcItemTopPos(innerIndex) < diff) {
            innerIndex++
        }
        return innerIndex - 1
    }

    private fun calcItemTopPos(index: Int) = index * rect.height()
}