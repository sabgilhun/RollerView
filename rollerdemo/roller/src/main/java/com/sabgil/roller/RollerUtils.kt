package com.sabgil.roller

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.sabgil.roller.models.RollingItem

fun resIdToRollingItem(
    context: Context, @DrawableRes id: Int
) = RollingItem(checkNotNull(ContextCompat.getDrawable(context, id)))
