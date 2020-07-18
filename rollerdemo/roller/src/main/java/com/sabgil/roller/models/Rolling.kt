package com.sabgil.roller.models

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.sabgil.roller.engines.NormalRollerEngine
import com.sabgil.roller.engines.RollerEngine
import com.sabgil.roller.framemappers.FlatFocusFrame
import com.sabgil.roller.framemappers.FocusFrame

class Rolling private constructor(
    val rollerEngine: RollerEngine,
    val focusFrame: FocusFrame
) {
    @DslMarker
    annotation class RollingSetupMarker

    @RollingSetupMarker
    class RollingSetup {
        private var rollerEngine: RollerEngine = NormalRollerEngine()
        private var focusFrameSetup: FocusFrameSetup = FocusFrameSetup()

        fun focusFrame(block: FocusFrameSetup.() -> Unit) {
            focusFrameSetup.block()
        }

        fun build() = Rolling(
            rollerEngine = this.rollerEngine,
            focusFrame = this.focusFrameSetup.build()
        )
    }

    @RollingSetupMarker
    class FocusFrameSetup {
        var width: Int = 0
        var height: Int = 0
        var circularLaneSetup: CircularLaneSetup = CircularLaneSetup()

        fun build() = FlatFocusFrame(
            definedWidth = width,
            definedHeight = height,
            circularLane = circularLaneSetup.build()
        )

        fun circularLane(block: CircularLaneSetup.() -> Unit) {
            circularLaneSetup.block()
        }

        @RollingSetupMarker
        class CircularLaneSetup {
            private var drawables: List<Drawable> = emptyList()

            fun images(context: Context, @DrawableRes vararg drawableId: Int) {
                drawables = drawableId.map {
                    requireNotNull(ContextCompat.getDrawable(context, it))
                }.toList()
            }

            fun build() = CircularLane(drawables)
        }
    }
}

fun rolling(block: Rolling.RollingSetup.() -> Unit) = Rolling.RollingSetup().apply(block).build()

