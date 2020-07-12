package com.sabgil.roller.engines

interface RollerEngine {

    val status: Status

    val output: Float

    fun start()
}

enum class Status {
    READY, STARTED, FINISHED
}