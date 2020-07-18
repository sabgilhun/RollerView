package com.sabgil.roller

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.sabgil.roller.models.RollerEngineStatus
import com.sabgil.roller.models.Rolling

class RollerView : View {

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    private var rolling: Rolling? = null
    private var lastOutput = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rolling?.let {
            when (it.rollerEngine.status) {
                RollerEngineStatus.READY -> {
                    it.focusFrame.draw(canvas, lastOutput)
                }
                RollerEngineStatus.STARTED -> {
                    lastOutput = it.rollerEngine.output
                    it.focusFrame.draw(canvas, lastOutput)
                    invalidate()
                }
                RollerEngineStatus.FINISHED -> {
                    it.focusFrame.draw(canvas, lastOutput)
                }
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rolling?.focusFrame?.resize(w, h)
    }

    fun roll(rolling: Rolling) {
        this.rolling = rolling
        rolling.focusFrame.resize(width, height)
        invalidate()
    }

    fun start() {
        rolling?.rollerEngine?.start()
        invalidate()
    }
}