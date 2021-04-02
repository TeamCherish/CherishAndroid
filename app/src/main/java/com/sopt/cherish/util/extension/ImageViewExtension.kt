package com.sopt.cherish.util.extension

import android.widget.ImageView
import com.sopt.cherish.util.PixelUtil.dp

object ImageViewExtension {
    fun ImageView.resizeImageView(width: Int, height: Int) {
        val params = this.layoutParams
        params.width = width.dp
        params.height = height.dp

        this.layoutParams = params
    }
}