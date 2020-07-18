package com.sabgil.roller.models

import android.content.Context
import android.graphics.Paint
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
        private var rollerEngineSetup: RollerEngineSetup = RollerEngineSetup()
        private var focusFrameSetup: FocusFrameSetup = FocusFrameSetup()

        fun engine(block: RollerEngineSetup.() -> Unit) {
            rollerEngineSetup.block()
        }

        fun frame(block: FocusFrameSetup.() -> Unit) {
            focusFrameSetup.block()
        }

        fun build() = Rolling(
            rollerEngine = this.rollerEngineSetup.build(),
            focusFrame = this.focusFrameSetup.build()
        )
    }

    @RollingSetupMarker
    class RollerEngineSetup {
        var type: RollerEngineType = RollerEngineType.NORMAL
        var duration: Long = 1000L
        var onRollingStart: (() -> Unit)? = null
        var onRollingEnd: (() -> Unit)? = null

        fun build() = when (type) {
            RollerEngineType.NORMAL -> NormalRollerEngine(duration).apply {
                this.onRollingStart = this@RollerEngineSetup.onRollingStart
                this.onRollingEnd = this@RollerEngineSetup.onRollingEnd
            }
        }
    }

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
    }
}

fun rolling(block: Rolling.RollingSetup.() -> Unit) = Rolling.RollingSetup().apply(block).build()

