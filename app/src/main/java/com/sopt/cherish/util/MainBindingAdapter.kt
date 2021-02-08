package com.sopt.cherish.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sopt.cherish.R

object MainBindingAdapter {
    // todo : BindingAdapter로 네이밍 다시
    @JvmStatic
    @BindingAdapter("android:setProfile")
    fun setProfile(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions.centerCropTransform())
            .into(imageView)
    }

    // dDay 조건은 변경을 좀 해줘야 함 현재 서버에도 더미데이터만 있어서
    @JvmStatic
    @BindingAdapter("android:waterVisibility")
    fun waterVisibility(imageView: ImageView, dDay: Int) {
        if (dDay < 7)
            imageView.visibility = View.VISIBLE
        else
            imageView.visibility = View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("android:allowChange")
    fun allowChange(imageView: ImageView, focus: Boolean) {
        if (focus) {
            imageView.setImageResource(R.drawable.icn_allow_top)
        } else {
            imageView.setImageResource(R.drawable.icn_allow)
        }
    }

    @JvmStatic
    @BindingAdapter("android:setPlantImage")
    fun setPlantImage(imageView: ImageView, plantImageUrl: String) {
        Glide.with(imageView)
            .load(plantImageUrl)
            .apply(RequestOptions.centerCropTransform())
            .into(imageView)
    }
}