package com.sabgil.roller.engines

import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

class NormalRollerEngine : RollerEngine {

    private val valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 3000
        doOnStart {
            _status = Status.STARTED
        }
        doOnEnd {
            _status = Status.FINISHED
        }

    }

    private var _status = Status.READY

    override val status: Status
        get() = _status

    override val output: Float
        get() = valueAnimator.animatedValue as Float

    override fun start() {
        valueAnimator.start()
    }
}