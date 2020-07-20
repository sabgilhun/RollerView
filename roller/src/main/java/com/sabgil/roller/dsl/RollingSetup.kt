package com.sabgil.roller.dsl

import android.content.Context
import com.sabgil.roller.models.Rolling

@RollingSetupMarker
class RollingSetup(context: Context) {
    private var rollerEngineSetup: RollerEngineSetup = RollerEngineSetup()
    private var focusFrameSetup: FocusFrameSetup = FocusFrameSetup(context)

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

fun rolling(context: Context, block: RollingSetup.() -> Unit) =
    RollingSetup(context).apply(block).build()