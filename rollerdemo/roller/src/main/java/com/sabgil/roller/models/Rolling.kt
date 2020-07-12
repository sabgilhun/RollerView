package com.sabgil.roller.models

import android.content.Context
import androidx.annotation.DrawableRes
import com.sabgil.roller.engines.NormalRollerEngine
import com.sabgil.roller.engines.RollerEngine
import com.sabgil.roller.framemappers.FlatFrame
import com.sabgil.roller.framemappers.Frame
import com.sabgil.roller.resIdToRollingItem

class Rolling private constructor(
    val rollerEngine: RollerEngine,
    val frame: Frame,
    val lane: Lane
) {
    @DslMarker
    annotation class RollingSetupMarker

    @RollingSetupMarker
    class RollingSetup {
        var rollerEngine: RollerEngine = NormalRollerEngine()
        var frame: Frame = FlatFrame()
        var lane: Lane? = null

        fun images(context: Context, @DrawableRes vararg drawableId: Int) {
            lane = Lane(context, drawableId.map { resIdToRollingItem(context, it) }.toList())
        }

        fun build() = Rolling(
            rollerEngine = this.rollerEngine,
            frame = this.frame,
            lane = this.lane!!
        )
    }
}

fun rolling(block: Rolling.RollingSetup.() -> Unit) = Rolling.RollingSetup().apply(block).build()

