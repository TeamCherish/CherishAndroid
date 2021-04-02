package com.sopt.cherish.util

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.R
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.PixelUtil.dp
import com.sopt.cherish.util.animation.ProgressbarAnimation
import kotlin.math.abs

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("android:setProfile")
    fun setProfile(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions.centerCropTransform())
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("android:waterVisibility")
    fun waterVisibility(imageView: ImageView, dDay: Int) {
        if (dDay <= 0)
            imageView.visibility = View.VISIBLE
        else
            imageView.visibility = View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("android:setPlantImage")
    fun setPlantImage(imageView: ImageView, plantImageUrl: String?) {
        Glide.with(imageView.context)
            .load(plantImageUrl)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["plantName", "dDay"], requireAll = true)
    fun setPlantBackground(imageView: ImageView, plantName: String?, dDay: Int) {
        if (dDay < 0) {
            // 시든상태
            imageView.setBackgroundColor(
                ContextCompat.getColor(
                    imageView.context,
                    R.color.cherish_light_black
                )
            )
        } else {
            // 멀쩡한 상태
            imageView.setBackgroundColor(
                ContextCompat.getColor(
                    imageView.context, when (plantName) {
                        "민들레" -> R.color.cherish_dandelion_background_color
                        "로즈마리" -> R.color.cherish_rosemary_background_color
                        "아메리칸블루" -> R.color.cherish_american_blue_background_color
                        "스투키" -> R.color.cherish_stuki_background_color
                        "단모환" -> R.color.cherish_sun_background_color
                        else -> R.color.white
                    }
                )
            )
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["isWatered", "remainDay", "wateredPlantName", "viewModel"])
    fun wateredBackground(
        imageView: ImageView,
        isWatered: Boolean?,
        remainDay: Int,
        plantName: String?,
        viewModel: MainViewModel
    ) {
        if (isWatered == true) {
            if (remainDay < 0) {
                imageView.setBackgroundColor(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.cherish_light_black
                    )
                )
            } else {
                imageView.setBackgroundColor(
                    ContextCompat.getColor(
                        imageView.context, R.color.cherish_watered_color
                    )
                )
                val delayHandler = Handler(imageView.context.mainLooper)
                delayHandler.postDelayed({
                    imageView.setBackgroundColor(
                        ContextCompat.getColor(
                            imageView.context, when (plantName) {
                                "민들레" -> R.color.cherish_dandelion_background_color
                                "로즈마리" -> R.color.cherish_rosemary_background_color
                                "아메리칸블루" -> R.color.cherish_american_blue_background_color
                                "스투키" -> R.color.cherish_stuki_background_color
                                "단모환" -> R.color.cherish_sun_background_color
                                else -> R.color.white
                            }
                        )
                    )
                }, 2000)
                viewModel.isWatered.value = null
            }
        }
        if (isWatered == false) {
            if (remainDay < 0) {
                imageView.setBackgroundColor(
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.cherish_light_black
                    )
                )
            } else {
                imageView.setBackgroundColor(
                    ContextCompat.getColor(
                        imageView.context, R.color.cherish_light_black
                    )
                )
                val delayHandler = Handler(imageView.context.mainLooper)
                delayHandler.postDelayed({
                    imageView.setBackgroundColor(
                        ContextCompat.getColor(
                            imageView.context, when (plantName) {
                                "민들레" -> R.color.cherish_dandelion_background_color
                                "로즈마리" -> R.color.cherish_rosemary_background_color
                                "아메리칸블루" -> R.color.cherish_american_blue_background_color
                                "스투키" -> R.color.cherish_stuki_background_color
                                "단모환" -> R.color.cherish_sun_background_color
                                else -> R.color.white
                            }
                        )
                    )
                }, 2000)
                viewModel.isWatered.value = null
            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:setSelectedPlantStrokeColor")
    fun setSelectedPlantStrokeColor(imageView: ImageView, plantName: String?) {
        imageView.setImageDrawable(
            ContextCompat.getDrawable(
                imageView.context, when (plantName) {
                    "민들레" -> R.drawable.home_selected_plant_indicator_dandelion
                    "로즈마리" -> R.drawable.home_selected_plant_indicator_rosemary
                    "아메리칸블루" -> R.drawable.home_selected_plant_indicator_american_blue
                    "스투키" -> R.drawable.home_selected_plant_indicator_stuki
                    "단모환" -> R.drawable.home_selected_plant_indicator_cactus
                    else -> R.drawable.home_selected_plant_indicator_rosemary
                }
            )
        )
    }

    @JvmStatic
    @BindingAdapter("android:setPlantImageViewSize")
    fun setPlantImageViewSize(imageView: ImageView, temp: Int) {
        imageView.apply {
            maxWidth = 207.dp
            minimumWidth = 207.dp
            maxHeight = 521.dp
            minimumHeight = 521.dp
        }
    }

    @JvmStatic
    @BindingAdapter("android:setBottomSheet")
    fun setBottomSheet(view: View, temp: Int) {
        val standardBottomSheetBehavior = BottomSheetBehavior.from(view)
        standardBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 160.dp
            expandedOffset = 100.dp
            halfExpandedRatio = 0.24f
            isHideable = false
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("android:setDeadLineDay")
    fun setDeadLineDay(textView: TextView, dDay: Int) {
        when {
            dDay > 0 -> {
                textView.text = "D-$dDay"
            }
            dDay < 0 -> {
                textView.text = "D+${abs(dDay)}"
            }
            else -> {
                textView.text = "D-DAY"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:setUserCount")
    fun setUserCount(textView: TextView, userCount: Int?) {
        textView.text = "$userCount"
    }

    @JvmStatic
    @BindingAdapter("android:setProgressbarAnimation")
    fun setProgressbarAnimation(progressbar: ProgressBar, to: Int) {
        val animation = ProgressbarAnimation(progressbar, to.toFloat())
        animation.duration = 2000
        progressbar.startAnimation(animation)
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("android:setProgressPercentText")
    fun setProgressPercentText(textView: TextView, percent: Int) {
        textView.text = "${percent}%"
    }

    @JvmStatic
    @BindingAdapter("android:setProgressBarBackground")
    fun setProgressbarBackground(progressbar: ProgressBar, rating: Int) {
        progressbar.progressDrawable = if (rating <= 30) {
            progressbar.context?.let {
                ResourcesCompat.getDrawable(
                    it.resources,
                    R.drawable.progress_drawable_verticle_red,
                    null
                )
            }
        } else {
            progressbar.context?.let {
                ResourcesCompat.getDrawable(
                    it.resources,
                    R.drawable.progress_drawable_vertical,
                    null
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter(value = ["userNickname", "selectedCherishName"])
    fun setReviewMainText(textView: TextView, userNickname: String, selectedCherishName: String?) {
        textView.text = "${userNickname}님!${selectedCherishName}님과의"
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("android:setReviewSubText")
    fun setReviewSubText(textView: TextView, selectedCherishName: String?) {
        textView.text = "${selectedCherishName}님과의 물주기를 기록해주세요"
    }
}