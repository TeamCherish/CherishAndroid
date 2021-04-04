package com.sopt.cherish.util.extension

import android.view.ViewGroup
import android.widget.ImageView
import com.sopt.cherish.MainApplication
import com.sopt.cherish.util.PixelUtil.dp

object ImageViewExtension {
    fun ImageView.matchSizeImageView() {
        val params = this.layoutParams
        params.width = MainApplication.pixelRatio.screenWidth
        params.height = MainApplication.pixelRatio.screenHeight
        this.layoutParams = params
    }

    fun ImageView.setMargin(start: Int, end: Int, top: Int, bottom: Int) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(start, top, end, bottom)
        layoutParams = params
    }

    fun ImageView.resizeImageView(width: Int, height: Int) {
        val params = this.layoutParams

        val screenWidth = MainApplication.pixelRatio.screenWidth
        val screenHeight = MainApplication.pixelRatio.screenHeight

        params.width = width.dp
        params.height = height.dp

        this.layoutParams = params
    }
}