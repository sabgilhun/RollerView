package com.sabgil.roller.framemappers

import android.graphics.Canvas
import com.sabgil.roller.models.Lane

interface Frame {

    fun resize(width: Int, height: Int)

    fun draw(canvas: Canvas, progress: Float, lane: Lane)
}