package com.sabgil.roller

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.sabgil.roller.engines.Status
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rolling?.let {
            when (it.rollerEngine.status) {
                Status.READY -> {
                    /* do nothing */
                }
                Status.STARTED -> {
                    it.frame.draw(canvas, it.rollerEngine.output, it.lane)
                    invalidate()
                }
                Status.FINISHED -> {
                    it.frame.draw(canvas, 1f, it.lane)
                }
            }
        }
    }

    fun roll(rolling: Rolling) {
        this.rolling = rolling
        rolling.rollerEngine.start()
        syncSize()
        invalidate()
    }

    private fun syncSize() {
        this.rolling?.let {
            it.frame.resize(width, height)
            it.lane.resize(width, 394)
        }
    }
}