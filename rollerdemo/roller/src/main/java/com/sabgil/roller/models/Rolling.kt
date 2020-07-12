package com.sabgil.roller.models

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.sabgil.roller.engines.NormalRollerEngine
import com.sabgil.roller.engines.RollerEngine
import com.sabgil.roller.framemappers.FlatFrame
import com.sabgil.roller.framemappers.Frame

class Rolling private constructor(
    val rollerEngine: RollerEngine,
    val frame: Frame,
    val lane: Lane
) {
    @DslMarker
    annotation class RollingSetupMarker

    @RollingSetupMarker
    class RollingSetup {
        private var rollerEngine: RollerEngine = NormalRollerEngine()
        private var frame: Frame = FlatFrame()
        private var lane: Lane? = null

        fun images(context: Context, @DrawableRes vararg drawableId: Int) {
            lane = Lane(drawableId.map {
                requireNotNull(ContextCompat.getDrawable(context, it))
            }.toList())
        }

        fun build() = Rolling(
            rollerEngine = this.rollerEngine,
            frame = this.frame,
            lane = requireNotNull(this.lane) { "lane value cannot be null" }
        )
    }
}

fun rolling(block: Rolling.RollingSetup.() -> Unit) = Rolling.RollingSetup().apply(block).build()

