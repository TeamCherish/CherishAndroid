package com.sopt.cherish.util.animation

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class ProgressbarAnimation(
    private val progressbar: ProgressBar,
    private val to: Float
) : Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = 0 + (to - 0) * interpolatedTime
        progressbar.progress = value.toInt()
    }
}