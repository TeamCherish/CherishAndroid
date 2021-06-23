package com.sopt.cherish.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.sopt.cherish.R
import com.sopt.cherish.util.PixelUtil.dp
import com.sopt.cherish.util.extension.ImageViewExtension.matchSizeImageView
import com.sopt.cherish.util.extension.ImageViewExtension.resizeImageView
import com.sopt.cherish.util.extension.ImageViewExtension.setMargin

object PlantImageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["delayPlantIsWatered", "delayPlantGrowth", "delayPlantName"])
    fun delayChangePlantImage(
        imageView: ImageView,
        delayPlantIsWatered: Boolean?,
        delayPlantGrowth: Int?,
        delayPlantName: String?
    ) {
        if (delayPlantIsWatered != null) {
            if (delayPlantIsWatered == false) {
                if (delayPlantGrowth != null) {
                    when {
                        delayPlantGrowth <= 25 -> {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (delayPlantName) {
                                        "민들레" -> R.drawable.dandelion_grayshadow_1
                                        "로즈마리" -> R.drawable.rosemary_grayshadow_1
                                        "아메리칸블루" -> R.drawable.americanblue_grayshadow_1
                                        "스투키" -> R.drawable.stuckyi_grayshadow_1
                                        "단모환" -> R.drawable.cactus_grayshadow_1
                                        else -> R.drawable.img_white
                                    }
                                )
                            imageView.apply {
                                when (delayPlantName) {
                                    "민들레" -> {
                                        resizeImageView(262, 331)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "로즈마리" -> {
                                        resizeImageView(220, 380)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 44.dp
                                        )
                                    }
                                    "아메리칸블루" -> {
                                        resizeImageView(249, 368)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "스투키" -> {
                                        resizeImageView(295, 266.6.toInt())
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "단모환" -> {
                                        resizeImageView(275, 229)
                                        setMargin(
                                            top = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp,
                                            bottom = 44.dp
                                        )
                                    }
                                    else -> {

                                    }
                                }
                            }
                        }
                        delayPlantGrowth in 26..50 -> {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (delayPlantName) {
                                        "민들레" -> R.drawable.dandelion_grayshadow_2
                                        "로즈마리" -> R.drawable.rosemary_grayshadow_2
                                        "아메리칸블루" -> R.drawable.americanblue_grayshadow_2
                                        "스투키" -> R.drawable.stuckyi_grayshadow_2
                                        "단모환" -> R.drawable.cactus_grayshadow_2
                                        else -> R.drawable.img_white
                                    }
                                )
                                .into(imageView)
                            imageView.apply {
                                when (delayPlantName) {
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
                                        resizeImageView(192, 500)
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
                                            end = 22.dp,
                                            bottom = 40.dp
                                        )
                                    }
                                    "스투키" -> {
                                        resizeImageView(240, 313)
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
                            }
                        }
                        else -> {
                            Glide.with(imageView)
                                .asDrawable()
                                .load(
                                    when (delayPlantName) {
                                        "민들레" -> R.drawable.dandelion_grayshadow_3
                                        "로즈마리" -> R.drawable.rosemary_grayshadow_3
                                        "아메리칸블루" -> R.drawable.americanblue_grayshadow_3
                                        "스투키" -> R.drawable.stuckyi_grayshadow_3
                                        "단모환" -> R.drawable.cactus_grayshadow_3
                                        else -> R.drawable.img_white
                                    }
                                )
                                .into(imageView)
                            imageView.apply {
                                when (delayPlantName) {
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
                            }
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["plantName", "growth", "dDay"])
    fun setPlantImage(
        imageView: ImageView,
        plantName: String?,
        growth: Int?,
        dDay: Int?
    ) {
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
                                )
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .transition(withCrossFade())
                                .into(imageView)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["setImageSizePlantName", "setImageSizeGrowth", "setImageSizedDay"])
    fun setPlantImageViewSize(
        imageView: ImageView,
        plantName: String?,
        growth: Int?,
        dDay: Int?,
    ) {
        if (growth != null) {
            imageView.apply {
                when {
                    growth <= 25 -> { // 1단계인 경우
                        when (plantName) {
                            "민들레" -> {
                                resizeImageView(262, 331)
                                setMargin(
                                    top = 0.dp,
                                    start = 0.dp,
                                    end = 0.dp,
                                    bottom = 40.dp
                                )
                            }
                            "로즈마리" -> {
                                resizeImageView(220, 380)
                                setMargin(
                                    top = 0.dp,
                                    start = 0.dp,
                                    end = 0.dp,
                                    bottom = 44.dp
                                )
                            }
                            "아메리칸블루" -> {
                                resizeImageView(249, 368)
                                setMargin(
                                    top = 0.dp,
                                    start = 0.dp,
                                    end = 0.dp,
                                    bottom = 40.dp
                                )
                            }
                            "스투키" -> {
                                resizeImageView(295, 266.6.toInt())
                                setMargin(
                                    top = 0.dp,
                                    start = 0.dp,
                                    end = 0.dp,
                                    bottom = 40.dp
                                )
                            }
                            "단모환" -> {
                                resizeImageView(275, 229)
                                setMargin(
                                    top = 0.dp,
                                    start = 0.dp,
                                    end = 0.dp,
                                    bottom = 44.dp
                                )
                            }
                            else -> {

                            }
                        }
                    }
                    growth in 26..50 -> { // 2단계인 경우
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
                                resizeImageView(192, 500)
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
                                    end = 22.dp,
                                    bottom = 40.dp
                                )
                            }
                            "스투키" -> {
                                resizeImageView(240, 313)
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
                    }
                    else -> { // 3단계인 경우
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
                            } else { // dDay가 0이 아닌 경우
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

}