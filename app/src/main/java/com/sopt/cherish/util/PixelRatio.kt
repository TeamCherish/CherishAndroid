package com.sopt.cherish.util

import android.app.Application
import androidx.annotation.Px
import kotlin.math.roundToInt

class PixelRatio(private val application: Application) {
    val displayMetrics
        get() = application.resources.displayMetrics

    val screenWidth
        get() = displayMetrics.widthPixels

    val screenHeight
        get() = displayMetrics.heightPixels

    @Px
    fun toPixel(dp: Int) = (dp * displayMetrics.density).roundToInt()

    fun toDP(@Px pixel: Int) = (pixel / displayMetrics.density).roundToInt()
}

