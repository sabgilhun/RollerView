package com.sabgil.roller.framemappers

import android.graphics.Canvas
import com.sabgil.roller.models.CircularLane

interface FocusFrame {

    fun resize(viewWidth: Int, viewHeight: Int)

    fun draw(canvas: Canvas, progress: Float)
}