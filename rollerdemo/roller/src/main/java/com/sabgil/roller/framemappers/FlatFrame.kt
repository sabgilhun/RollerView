package com.sabgil.roller.framemappers

import android.graphics.Canvas
import android.graphics.Rect
import com.sabgil.roller.models.Lane

class FlatFrame : Frame {
    private var frameRect = Rect(0, 0, 0, 0)

    override fun resize(width: Int, height: Int) {
        frameRect = Rect(0, 0, width, height)
    }

    override fun draw(canvas: Canvas, progress: Float, lane: Lane) {
        lane.combinedBitmap?.let {
            canvas.drawBitmap(it, calcRect(progress, lane.length), frameRect, null)
        }
    }

    private fun calcRect(progress: Float, laneLength: Int): Rect {
        val inverseProgress = 1 - progress
        val endLength = laneLength - frameRect.height()

        return Rect(
            0, (inverseProgress * endLength).toInt(),
            frameRect.width(), (inverseProgress * endLength).toInt() + frameRect.height()
        )
    }
}