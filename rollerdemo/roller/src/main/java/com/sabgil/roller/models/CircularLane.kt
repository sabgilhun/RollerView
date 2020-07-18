package com.sabgil.roller.models

import android.graphics.drawable.Drawable

class CircularLane(
    items: List<Drawable>,
    private var numberOfCycle: Int,
    private var targetIndex: Int
) {

    private val circularItems = CircularList(items)
    var totalLen: Int = 0

    fun resize(height: Int) {
        this.totalLen = height * (circularItems.size * numberOfCycle + targetIndex)
    }

    fun getItem(index: Int): Drawable = circularItems[index]

    private class CircularList<T>(private val elements: List<T>) {

        val size = elements.size

        private fun mapToCircularIndex(index: Int): Int = when {
            index >= elements.size -> index % elements.size
            index < 0 -> (index % elements.size).let { if (it == 0) 0 else elements.size + it }
            else -> index
        }

        operator fun get(index: Int) = elements[mapToCircularIndex(index)]
    }
}