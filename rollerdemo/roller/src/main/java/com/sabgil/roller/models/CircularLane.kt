package com.sabgil.roller.models

import android.graphics.drawable.Drawable

class CircularLane(private val items: List<Drawable>) {

    var length: Int = 0
    private var width: Int = 0
    private var height: Int = 0

    fun resize(width: Int, height: Int) {
        this.length = height * items.size
        this.width = width
        this.height = height
    }

    fun getCircularItem(index: Int): Drawable {
        return items[
                when {
                    index >= items.size -> index % items.size
                    index < 0 -> when (val mod = index % items.size) {
                        0 -> 0
                        else -> items.size + mod
                    }
                    else -> index
                }
        ]
    }
}