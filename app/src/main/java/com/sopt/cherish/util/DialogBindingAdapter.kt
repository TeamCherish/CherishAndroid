package com.sopt.cherish.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object DialogBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:delayVisiblity")
    fun setDelayVisibility(textView: TextView, dDay: Int) {
        if (dDay < 0)
            textView.visibility = View.INVISIBLE
        else
            textView.visibility = View.VISIBLE
    }
}