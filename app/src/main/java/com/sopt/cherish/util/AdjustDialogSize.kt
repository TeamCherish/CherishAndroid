package com.sopt.cherish.util

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

/**
 * Create on 2021-1-1 by SSong-develop
 * dialog 크기를 재설정 해주는 함수
 */
class AdjustDialog constructor(private val context: Context) {
    fun adjustSize(dialogFragment: DialogFragment, widthRatio: Float, heightRatio: Float) {
        val params: WindowManager.LayoutParams = dialogFragment.dialog!!.window!!.attributes
        val size = Point()
        val display = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context.display
        } else {
            @Suppress("DEPRECATION")
            (context as Activity).windowManager.defaultDisplay
        }

        display?.getRealSize(size)
        params.width = (size.x * widthRatio).toInt()
        params.height = (size.y * heightRatio).toInt()

        dialogFragment.dialog!!.window!!.attributes = params
    }

}