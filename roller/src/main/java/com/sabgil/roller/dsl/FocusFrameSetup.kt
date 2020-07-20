package com.sabgil.roller.dsl

import android.content.Context
import android.graphics.Paint
import androidx.annotation.Px
import com.sabgil.roller.frames.FlatHorizontalFocusFrame
import com.sabgil.roller.frames.FlatVerticalFocusFrame
import com.sabgil.roller.frames.FocusFrame
import com.sabgil.roller.models.Orientation

@RollingSetupMarker
class FocusFrameSetup(context: Context) {
    @Px
    var width: Int = 0
    @Px
    var height: Int = 0

    var framePaint: Paint? = null
    var orientation: Orientation = Orientation.UP
    var circularLaneSetup: CircularLaneSetup = CircularLaneSetup(context)

    fun build(): FocusFrame = when (orientation) {
        Orientation.UP, Orientation.DOWN -> FlatVerticalFocusFrame(
            orientation = orientation,
            definedWidth = width,
            definedHeight = height,
            circularLane = circularLaneSetup.build(),
            paint = framePaint
        )
        Orientation.LEFT, Orientation.RIGHT -> FlatHorizontalFocusFrame(
            orientation = orientation,
            definedWidth = width,
            definedHeight = height,
            circularLane = circularLaneSetup.build(),
            paint = framePaint
        )
    }

    fun lane(block: CircularLaneSetup.() -> Unit) {
        circularLaneSetup.block()
    }
}