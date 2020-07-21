package com.sabgil.roller.dsl

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.sabgil.roller.models.CircularLane

@RollingSetupMarker
class CircularLaneSetup(private val context: Context) {
    private var drawables: List<Drawable> = emptyList()
    var numberOfCycle: Int = 1
    var targetIndex: Int = 0

    fun drawableIds(@DrawableRes vararg drawableIds: Int) {
        drawables = drawableIds.map {
            requireDrawable(ContextCompat.getDrawable(context, it))
        }.toList()
    }

    fun drawableIds(drawableIds: List<Int>) {
        drawables = drawableIds.map {
            requireDrawable(ContextCompat.getDrawable(context, it))
        }.toList()
    }

    fun drawables(drawables: List<Drawable>) {
        this.drawables = drawables.toList()
    }

    fun build(): CircularLane {
        checkConditionForBuild()
        return CircularLane(drawables, numberOfCycle, targetIndex)
    }

    private fun checkConditionForBuild() {
        require(drawables.isNotEmpty()) {
            "Rolling items must not empty."
        }
        require(drawables.size > targetIndex) {
            "Target index must not exceed to size of items"
        }
    }

    private fun requireDrawable(drawable: Drawable?): Drawable =
        requireNotNull(drawable) {
            "Drawable must not null, check drawable id"
        }

}