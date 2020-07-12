package com.sabgil.roller

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
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
        if (rolling?.rollerEngine?.status == Status.STARTED) {
            rolling?.frame?.draw(canvas, rolling?.rollerEngine?.output!!, rolling?.lane!!)
            invalidate()
        } else if (rolling?.rollerEngine?.status == Status.FINISHED) {
            rolling?.frame?.draw(canvas, 1f, rolling?.lane!!)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        rolling?.frame?.resize(width, height)
    }

    fun roll(rolling: Rolling) {
        this.rolling = rolling
        rolling.rollerEngine.start()
        rolling.frame.resize(width, height)
        invalidate()
    }
}