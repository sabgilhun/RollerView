package com.sabgil.roller.engines

import com.sabgil.roller.models.RollerEngineStatus

interface RollerEngine {

    val status: RollerEngineStatus

    val output: Float

    var duration: Long

    var onRollingStart: (() -> Unit)?

    var onRollingEnd: (() -> Unit)?

    fun start()
}