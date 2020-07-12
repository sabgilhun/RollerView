package com.sabgil.roller.models

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable

class Lane(private val items: List<Drawable>) {

    var length: Int = 0
    var combinedBitmap: Bitmap? = null
    private var width: Int = 0
    private var height: Int = 0

    fun resize(width: Int, height: Int) {
        if (width > 0 && height > 0 && this.width != width && this.height != height) {
            this.length = height * items.size
            this.width = width
            this.height = height
            combinedBitmap(width, height, length)
        }
    }

    private fun combinedBitmap(width: Int, height: Int, length: Int) {
        combinedBitmap = Bitmap.createBitmap(
            width, length,
            Bitmap.Config.ARGB_8888
        ).apply {
            var accLength = 0
            val canvas = Canvas(this)

            items.forEach {
                it.bounds = Rect(
                    0, accLength,
                    width, accLength + height
                )
                it.draw(canvas)
                accLength += height
            }
        }
    }
}