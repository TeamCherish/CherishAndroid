package com.sopt.cherish.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.MainApplication
import com.sopt.cherish.R
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.PixelUtil.dp
import com.sopt.cherish.util.animation.ProgressbarAnimation
import kotlin.math.abs

/**
 * Create by SSong-develop
 * need to divide
 */
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

    // WateringAnimation works in 3.2 seconds
    // 3380L 까지도 겹쳐지게 보여졌단 말이야
    // 그럼 이제 3390L 부터 생각을 좀 해보면 될거 같은데;;;
    // 아니면 3420~3450 사이일 수도 있다.
    // 이게 10 몇번을 해서 겹치게 된다고 가정해보면
    // 3570L을 기준으로 잡아보면 될지도 모르겠다
    // 3500 ~ 3570 사이 일거같음 애니메이션 로딩하는게
    @JvmStatic
    @BindingAdapter("android:wateringAnimation")
    fun wateringAnimation(imageView: ImageView, isWatered: Boolean?) {
        val fadeAnimation =
            AnimationUtils.loadAnimation(imageView.context, R.anim.waterinf_fade_out)
        if (isWatered != null) {
            if (isWatered == true) {
                imageView.visibility = View.VISIBLE
                imageView.animation = fadeAnimation
                Glide.with(imageView.context)
                    .asGif()
                    .load(R.raw.watering)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView)
                val delayHandler = Handler(imageView.context.mainLooper)
                delayHandler.postDelayed({
                    imageView.visibility = View.INVISIBLE
                }, 3500L)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["plantName", "dDay"], requireAll = true)
    fun setPlantBackground(imageView: ImageView, plantName: String?, dDay: Int) {
        if (dDay < 0) {
            imageView.setBackgroundColor(
                ContextCompat.getColor(
                    imageView.context,
                    R.color.cherish_wither_background_color
                )
            )
        } else {
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
                val valueAnimator = ValueAnimator.ofObject(
                    ArgbEvaluator(),
                    ContextCompat.getColor(
                        imageView.context,
                        R.color.cherish_wither_background_color
                    ),
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
                ).apply {
                    // 이 duration이 너무 느려서 안맞는 현상이 발생한다.
                    duration = 2800
                    addUpdateListener {
                        imageView.setBackgroundColor(it.animatedValue as Int)
                    }
                }
                valueAnimator.start()
            }
            viewModel.isWatered.value = null
        }
        if (isWatered == false) {
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
            }, 3300)
            viewModel.isWatered.value = null
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
    @BindingAdapter("android:setBottomSheet")
    fun setBottomSheet(view: View, temp: Int) {
        // 160.dp 기준 0.208f가 비율 똑같음
        val standardBottomSheetBehavior = BottomSheetBehavior.from(view)
        standardBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            isFitToContents = false
            peekHeight = (MainApplication.pixelRatio.screenHeight.dp / 5.dp)
            halfExpandedRatio = 0.23f // 이거 비율만 좀 수정해주면 될듯?
            expandedOffset = 100.dp
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
    fun setReviewMainText(
        textView: TextView,
        userNickname: String,
        selectedCherishName: String?
    ) {
        textView.text = "${userNickname}님!${selectedCherishName}와(과)의"
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("android:setReviewSubText")
    fun setReviewSubText(textView: TextView, selectedCherishName: String?) {
        textView.text = "${selectedCherishName}와(과)의 물주기를 기록해주세요"
    }
}