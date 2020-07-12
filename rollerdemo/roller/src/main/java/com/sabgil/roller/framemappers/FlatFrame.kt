package com.sabgil.roller.framemappers

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import com.sabgil.roller.models.Lane

class FlatFrame : Frame {

    private var frameWidth: Int = 0

    private var frameHeight: Int = 0

    override fun resize(width: Int, height: Int) {
        this.frameWidth = width
        this.frameHeight = height
    }

    override fun draw(canvas: Canvas, progress: Float, lane: Lane) {
        val bitmap = (lane.combinedImage as BitmapDrawable).bitmap
        val rect = Rect(0, 0, frameWidth, frameHeight)
        canvas.drawBitmap(bitmap, calcRect(progress, lane.length), rect, null)
    }

    private fun calcRect(progress: Float, laneLength: Int): Rect = Rect(
        0,
        ((1 - progress) * laneLength * 2 / 3).toInt(),
        frameWidth,
        ((1 - progress) * laneLength * 2 / 3).toInt() + frameHeight
    )
}