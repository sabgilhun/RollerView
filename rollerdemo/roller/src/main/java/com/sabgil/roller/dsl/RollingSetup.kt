package com.sabgil.roller.dsl

import com.sabgil.roller.models.Rolling

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

fun rolling(block: RollingSetup.() -> Unit) = RollingSetup().apply(block).build()