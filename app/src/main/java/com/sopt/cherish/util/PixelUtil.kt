package com.sopt.cherish.util

import androidx.annotation.Px
import com.sopt.cherish.MainApplication

object PixelUtil {
    // naming 다시
    val Number.pixel: Int
        @Px get() = MainApplication.pixelRatio.toDP(this.toInt())

    val Number.dp: Int
        get() = MainApplication.pixelRatio.toPixel(this.toInt())

    val Number.pixelFloat: Float
        @Px get() = MainApplication.pixelRatio.toDP(this.toInt()).toFloat()

    val Number.dpFloat: Float
        get() = MainApplication.pixelRatio.toPixel(this.toInt()).toFloat()

    val screenWidth: Int
        get() = MainApplication.pixelRatio.screenWidth

    val screenHeight: Int
        get() = MainApplication.pixelRatio.screenHeight
}