package com.sabgil.roller.framemappers

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import com.sabgil.roller.models.Lane

class FlatFrame : Frame {
    var width = 100
    var height = 394

    private var viewWidth = 100
    private var viewHeight = 300

    private var preBuffer = (viewHeight + height) / 2

    override fun resize(width: Int, height: Int) {
        this.width = width
        viewHeight = height
        preBuffer = (viewHeight - this.height) / 2
    }

    override fun draw(canvas: Canvas, progress: Float, lane: Lane) {
        val position = calcCurrentPosWithProgress(1 - progress, lane)
        val displayItemRange = calcDisplayItemRange(position)
        val offset = calcViewPosDiff(position)

        for (i in displayItemRange) {
            lane.getCircularItem(i).apply {
                bounds =
                    Rect(0, calcItemTopPos(i) - offset, width, calcItemTopPos(i) - offset + height)
            }.draw(canvas)
        }
    }

    private fun calcCurrentPosWithProgress(progress: Float, lane: Lane): Int =
        (progress * lane.length).toInt()

    private fun calcViewPosDiff(progress: Int): Int = progress - preBuffer

    private fun calcBoundary(): Int = (preBuffer + height)

    private fun calcBoundary2(): Int = (preBuffer + height * 2)

    private fun calcDisplayItemRange(progress: Int): IntRange {
        val diff = progress - calcBoundary()
        val first = if (calcItemTopPos(0) > diff) {
            // find pre
            checkPreviousItem(diff)
        } else {
            // find next
            checkNextItem(diff)
        }

        val last = checkNexOverItem(first, progress + calcBoundary2())

        return first..last
    }

    private fun checkPreviousItem(diff: Int): Int {
        var innerIndex = -1
        while (calcItemTopPos(innerIndex) > diff) {
            innerIndex--
        }
        return innerIndex + 1
    }

    private fun checkNextItem(diff: Int): Int {
        var innerIndex = 1
        while (calcItemTopPos(innerIndex) < diff) {
            innerIndex++
        }
        return innerIndex
    }

    private fun checkNexOverItem(initIndex: Int, diff: Int): Int {
        var innerIndex = initIndex
        while (calcItemTopPos(innerIndex) < diff) {
            innerIndex++
        }
        return innerIndex - 1
    }

    private fun calcItemTopPos(index: Int) = index * height

    // 뷰의 0이 프로그레스로 했을때 몇인지 체크 (progress - buffer)
    // 첫번째 보이는 아이템 찾기 index
    // 각 이미지 top pos > progress + buffer + frame height을 성립하는 최초 index
    // 각 index 시작은 index * frame height
    // 찾은 index부터 추가해가면서 안보이는 index까지 반복해서 그림
}