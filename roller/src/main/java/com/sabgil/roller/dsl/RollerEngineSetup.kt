package com.sabgil.roller.dsl

import com.sabgil.roller.engines.NormalRollerEngine
import com.sabgil.roller.models.RollerEngineType

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