package com.sabgil.roller.engines

import android.animation.ValueAnimator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.sabgil.roller.models.RollerEngineStatus

class NormalRollerEngine(
    duration: Long = 3000L
) : RollerEngine {

    private var _status = RollerEngineStatus.READY
    private var _onRollingStart: (() -> Unit)? = null
    private var _onRollingEnd: (() -> Unit)? = null

    private val valueAnimator =
        ValueAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            doOnStart {
                _status = RollerEngineStatus.STARTED
                onRollingStart?.invoke()
            }
            doOnEnd {
                _status = RollerEngineStatus.FINISHED
                onRollingEnd?.invoke()
            }
        }

    override val status: RollerEngineStatus
        get() = _status

    override val output: Float
        get() = valueAnimator.animatedValue as Float

    override var onRollingStart: (() -> Unit)?
        get() = _onRollingStart
        set(value) {
            _onRollingStart = value
        }

    override var onRollingEnd: (() -> Unit)?
        get() = _onRollingEnd
        set(value) {
            _onRollingEnd = value
        }

    override fun start() {
        valueAnimator.start()
    }
}