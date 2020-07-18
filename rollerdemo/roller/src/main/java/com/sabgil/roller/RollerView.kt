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
                    it.focusFrame.draw(canvas, it.rollerEngine.output)
                    invalidate()
                }
                Status.FINISHED -> {
                    it.focusFrame.draw(canvas, 1f)
                }
            }
        }
    }

    fun roll(rolling: Rolling) {
        this.rolling = rolling
        rolling.focusFrame.resize(width, height)
        rolling.rollerEngine.start()
        invalidate()
    }
}