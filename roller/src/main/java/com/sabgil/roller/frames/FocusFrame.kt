package com.sabgil.roller.frames

import android.graphics.Canvas

interface FocusFrame {

    fun resize(viewWidth: Int, viewHeight: Int)

    fun draw(canvas: Canvas, progress: Float)
}