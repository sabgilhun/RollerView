package com.sabgil.roller.dsl

import com.sabgil.roller.engines.LinearRollerEngine
import com.sabgil.roller.engines.AccelerateDecelerateRollerEngine
import com.sabgil.roller.engines.RollerEngine
import com.sabgil.roller.models.RollerEngineType

@RollingSetupMarker
class RollerEngineSetup {
    var type: RollerEngineType = RollerEngineType.NORMAL
    var duration: Long = 1000L
    var customRollerEngine: RollerEngine? = null
    private var onRollingStart: (() -> Unit)? = null
    private var onRollingEnd: (() -> Unit)? = null

    fun onRollingStart(block: () -> Unit) {
        onRollingStart = block
    }

    fun onRollingEnd(block: () -> Unit) {
        this.onRollingEnd = block
    }

    fun build() = when (type) {
        RollerEngineType.NORMAL -> LinearRollerEngine().setupRollerEngine()
        RollerEngineType.ACCELERATE_DECELERATE -> AccelerateDecelerateRollerEngine().setupRollerEngine()
        RollerEngineType.CUSTOM -> requireNotNull(customRollerEngine) {
            "when use custom type engine, `customRollerEngine` value must be not null"
        }.setupRollerEngine()
    }

    private fun RollerEngine.setupRollerEngine(): RollerEngine {
        this.duration = this@RollerEngineSetup.duration
        this.onRollingStart = this@RollerEngineSetup.onRollingStart
        this.onRollingEnd = this@RollerEngineSetup.onRollingEnd
        return this
    }
}