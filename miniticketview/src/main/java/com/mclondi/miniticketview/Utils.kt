package com.mclondi.miniticketview

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue


fun Context.dpToPx(dp: Float): Int {
    return resources.dpToPx(dp)
}

fun Resources.dpToPx(dp: Float): Int {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
    return px.toInt()
}

