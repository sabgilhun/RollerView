package com.sabgil.roller.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

class Lane(
    context: Context,
    var items: List<RollingItem>
) {
    val length: Int = items.foldRight(0) { item, acc -> item.height + acc }
    val width: Int = items.foldRight(0) { item, acc ->
        if (acc < item.width) item.width
        else acc
    }
    var combinedImage: Drawable

    init {

        val bitmap = Bitmap.createBitmap(width, length, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        var accLength = 0
        items.forEach {
            with(it.drawable) {
                bounds = Rect(
                    (0),
                    accLength,
                    intrinsicWidth,
                    (accLength + intrinsicHeight)
                )
                draw(canvas)
                accLength += intrinsicHeight
            }
        }
        combinedImage = BitmapDrawable(context.resources, bitmap)
    }
}