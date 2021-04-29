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
import com.sopt.cherish.util.PixelUtil.screenHeight
import com.sopt.cherish.util.PixelUtil.screenWidth
import com.sopt.cherish.util.animation.ProgressbarAnimation
import com.sopt.cherish.util.extension.ImageViewExtension.matchSizeImageView
import com.sopt.cherish.util.extension.ImageViewExtension.resizeImageView
import com.sopt.cherish.util.extension.ImageViewExtension.setMargin
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
                    .override((screenWidth / 3), screenHeight)
                    .load(R.raw.watering)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView)
                val delayHandler = Handler(imageView.context.mainLooper)
                delayHandler.postDelayed({
                    imageView.visibility = View.INVISIBLE
                }, 2700L)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["plantName", "growth", "dDay"])
    fun setPlantImage(imageView: ImageView, plantName: String?, growth: Int?, dDay: Int?) {
        if (growth != null) {
            when {
                growth <= 25 -> {
                    if (dDay != null) {
                        if (dDay < 0) {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (plantName) {
                                        "민들레" -> R.drawable.dandelion_grayshadow_1
                                        "로즈마리" -> R.drawable.rosemary_grayshadow_1
                                        "아메리칸블루" -> R.drawable.americanblue_grayshadow_1
                                        "스투키" -> R.drawable.stuckyi_grayshadow_1
                                        "단모환" -> R.drawable.cactus_grayshadow_1
                                        else -> R.drawable.img_white
                                    }
                                )
                                .into(imageView)
                        } else {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (plantName) {
                                        "민들레" -> R.drawable.img_dandelion_1
                                        "로즈마리" -> R.drawable.img_rose_1
                                        "아메리칸블루" -> R.drawable.img_americanblue_1
                                        "스투키" -> R.drawable.img_stuki_1
                                        "단모환" -> R.drawable.img_sun_1
                                        else -> R.drawable.img_white
                                    }
                                )
                                .into(imageView)
                        }
                    }
                }
                growth in 26..50 -> {
                    if (dDay != null) {
                        if (dDay < 0) {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (plantName) {
                                        "민들레" -> R.drawable.dandelion_grayshadow_2
                                        "로즈마리" -> R.drawable.rosemary_grayshadow_2
                                        "아메리칸블루" -> R.drawable.americanblue_grayshadow_2
                                        "스투키" -> R.drawable.stuckyi_grayshadow_2
                                        "단모환" -> R.drawable.cactus_grayshadow_2
                                        else -> R.drawable.img_white
                                    }
                                )
                                .into(imageView)
                        } else {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (plantName) {
                                        "민들레" -> R.drawable.img_dandelion_2
                                        "로즈마리" -> R.drawable.img_rose_2
                                        "아메리칸블루" -> R.drawable.img_americanblue_2
                                        "스투키" -> R.drawable.img_stuki_2
                                        "단모환" -> R.drawable.img_sun_2
                                        else -> R.drawable.img_white
                                    }
                                )
                                .into(imageView)
                        }
                    }
                }
                else -> {
                    if (dDay != null) {
                        if (dDay < 0) {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (plantName) {
                                        "민들레" -> R.drawable.dandelion_grayshadow_3
                                        "로즈마리" -> R.drawable.rosemary_grayshadow_3
                                        "아메리칸블루" -> R.drawable.americanblue_grayshadow_3
                                        "스투키" -> R.drawable.stuckyi_grayshadow_3
                                        "단모환" -> R.drawable.cactus_grayshadow_3
                                        else -> R.drawable.img_white
                                    }
                                )
                                .into(imageView)
                        } else {
                            Glide.with(imageView)
                                .asGif()
                                .load(
                                    when (plantName) {
                                        "민들레" -> R.raw.min_android
                                        "로즈마리" -> R.raw.rose_android
                                        "아메리칸블루" -> R.raw.blue_android
                                        "스투키" -> R.raw.stuki_android
                                        "단모환" -> R.raw.sun_android
                                        else -> R.raw.cherish_loading
                                    }
                                ).into(imageView)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["setImageSizePlantName", "setImageSizeGrowth", "setImageSizedDay"])
    fun setPlantImageViewSize(imageView: ImageView, plantName: String?, growth: Int?, dDay: Int?) {
        if (growth != null) {
            imageView.apply {
                when {
                    growth <= 25 -> {
                        when (plantName) {
                            "민들레" -> {
                                resizeImageView(262, 331)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 40.dp)
                            }
                            "로즈마리" -> {
                                resizeImageView(220, 380)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 44.dp)
                            }
                            "아메리칸블루" -> {
                                resizeImageView(249, 368)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 40.dp)
                            }
                            "스투키" -> {
                                resizeImageView(295, 266.6.toInt())
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 40.dp)
                            }
                            "단모환" -> {
                                resizeImageView(275, 229)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 44.dp)
                            }
                            else -> {

                            }
                        }
                    }
                    growth in 26..50 -> {
                        when (plantName) {
                            "민들레" -> {
                                resizeImageView(235, 388)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 40.dp)
                            }
                            "로즈마리" -> {
                                resizeImageView(192, 500)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 40.dp)
                            }
                            "아메리칸블루" -> {
                                resizeImageView(204, 461)
                                setMargin(top = 0.dp, start = 0.dp, end = 22.dp, bottom = 40.dp)
                            }
                            "스투키" -> {
                                resizeImageView(240, 313)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 40.dp)
                            }
                            "단모환" -> {
                                resizeImageView(283, 350)
                                setMargin(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 40.dp)
                            }
                            else -> {

                            }
                        }
                    }
                    else -> {
                        if (dDay != null) {
                            if (dDay < 0) {
                                when (plantName) {
                                    "민들레" -> {
                                        resizeImageView(235, 388)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "로즈마리" -> {
                                        resizeImageView(204, 500)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "아메리칸블루" -> {
                                        resizeImageView(204, 461)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "스투키" -> {
                                        resizeImageView(305, 440)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "단모환" -> {
                                        resizeImageView(283, 350)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    else -> {

                                    }
                                }
                            } else {
                                when (plantName) {
                                    "민들레" -> {
                                        matchSizeImageView()
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 0.dp
                                        )
                                    }
                                    "로즈마리" -> {
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 0.dp
                                        )
                                        matchSizeImageView()
                                    }
                                    "아메리칸블루" -> {
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 0.dp
                                        )
                                        matchSizeImageView()
                                    }
                                    "스투키" -> {
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 0.dp
                                        )
                                        matchSizeImageView()
                                    }
                                    "단모환" -> {
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 0.dp
                                        )
                                        matchSizeImageView()
                                    }
                                    else -> {

                                    }
                                }
                            }
                        }
                    }
                }
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
                    duration = 3000
                    addUpdateListener {
                        imageView.setBackgroundColor(it.animatedValue as Int)
                    }
                }
                valueAnimator.start()
            }
            viewModel.isWatered.value = null
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
                }, 3000)
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