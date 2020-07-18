package com.sabgil.roller.framemappers

import android.graphics.Canvas
import android.graphics.Rect
import com.sabgil.roller.models.CircularLane

class FlatFocusFrame(
    private val definedWidth: Int,
    private val definedHeight: Int,
    private val circularLane: CircularLane
) : FocusFrame {
    private var viewWidth = 0
    private var viewHeight = 0
    private var frameRect: Rect = Rect()
    private var leadingSpace = 0
    private var trailingSpace = 0
    private var firstItemBoundary = 0
    private var lastItemBoundary = 0

    override fun resize(viewWidth: Int, viewHeight: Int) {
        if (this.viewHeight != viewHeight || this.viewWidth != viewWidth) {
            this.viewHeight = viewHeight
            this.viewWidth = viewWidth
            updateFrameRect()
            circularLane.resize(frameRect.width(), frameRect.height())
        }
    }

    override fun draw(canvas: Canvas, progress: Float) {
        val position = calcCurrentPosWithProgress(1 - progress, circularLane)
        val displayItemRange = calcDisplayItemRange(position)
        val offset = calcViewPosDiff(position)

        for (i in displayItemRange) {
            val diff = calcItemTopPos(i) - offset
            val drawable = circularLane.getCircularItem(i)
            drawable.bounds = Rect(frameRect).apply { offset(0, diff) }
            drawable.draw(canvas)
        }
    }

    private fun updateFrameRect() {
        val (start, end) = coerce(viewWidth, definedWidth)
        val (top, bottom) = coerce(viewHeight, definedHeight)
        frameRect = Rect(start, top, end, bottom)
        leadingSpace = (viewHeight - frameRect.height()) / 2
        trailingSpace = (viewHeight + frameRect.height()) / 2
        firstItemBoundary = leadingSpace + frameRect.height()
        lastItemBoundary = trailingSpace + frameRect.height()
    }

    private fun coerce(limit: Int, actual: Int): Pair<Int, Int> =
        if (limit <= actual) {
            0 to viewWidth
        } else {
            val paddingStart = (viewWidth - definedWidth) / 2
            val paddingEnd = viewWidth - definedWidth - paddingStart
            paddingStart to viewWidth - paddingEnd
        }

    private fun calcCurrentPosWithProgress(progress: Float, circularLane: CircularLane): Int =
        (progress * circularLane.length).toInt()

    private fun calcViewPosDiff(progress: Int): Int = progress - leadingSpace

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

    private fun calcItemTopPos(index: Int) = index * frameRect.height()
}