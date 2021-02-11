package com.sopt.cherish.util.animation

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class ProgressbarAnimation(
    private val progressbar: ProgressBar,
    private val to: Float
) : Animation() {
    // from을 0으로 하고 to를 원하는 값으로 설정해서? 한다라고 생각하면 될거같음
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = 0 + (to - 0) * interpolatedTime
        progressbar.progress = value.toInt()
    }
}