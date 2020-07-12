package com.sabgil.roller.models

import android.graphics.drawable.Drawable

data class RollingItem(
    val drawable: Drawable,
    val width: Int = drawable.intrinsicWidth,
    val height: Int = drawable.intrinsicHeight
)