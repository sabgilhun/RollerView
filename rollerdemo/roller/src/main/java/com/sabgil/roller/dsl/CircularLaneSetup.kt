package com.sabgil.roller.dsl

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.sabgil.roller.models.CircularLane

@RollingSetupMarker
class CircularLaneSetup {
    private var drawables: List<Drawable> = emptyList()
    var numberOfCycle: Int = 1
    var targetIndex: Int = 0

    fun images(context: Context, @DrawableRes vararg drawableId: Int) {
        drawables = drawableId.map {
            requireNotNull(ContextCompat.getDrawable(context, it))
        }.toList()
    }

    fun build(): CircularLane {
        require(drawables.isNotEmpty()) {
            "Rolling items must not empty."
        }
        require(drawables.size > targetIndex) {
            "Target index must not exceed to size of items"
        }
        return CircularLane(drawables, numberOfCycle, targetIndex)
    }
}