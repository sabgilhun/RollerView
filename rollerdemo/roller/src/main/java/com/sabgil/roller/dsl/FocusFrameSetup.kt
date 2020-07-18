package com.sabgil.roller.dsl

import android.graphics.Paint
import com.sabgil.roller.frames.FlatFocusFrame
import com.sabgil.roller.models.Orientation

@RollingSetupMarker
class FocusFrameSetup {
    var width: Int = 0
    var height: Int = 0
    var framePaint: Paint? = null
    var orientation: Orientation = Orientation.UP
    var circularLaneSetup: CircularLaneSetup = CircularLaneSetup()

    fun build() = FlatFocusFrame(
        orientation = orientation,
        definedWidth = width,
        definedHeight = height,
        circularLane = circularLaneSetup.build(),
        paint = framePaint
    )

    fun lane(block: CircularLaneSetup.() -> Unit) {
        circularLaneSetup.block()
    }
}