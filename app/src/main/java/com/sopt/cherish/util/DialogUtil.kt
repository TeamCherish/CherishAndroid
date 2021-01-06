package com.sopt.cherish.util

import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.MainApplication

/**
 * Create on 2021-1-1 by SSong-develop
 * dialog 크기를 재설정 해주는 함수
 */
object DialogUtil {
    fun adjustDialogSize(dialogFragment: DialogFragment, widthRatio: Float, heightRatio: Float) {
        val dialogParams: WindowManager.LayoutParams = dialogFragment.dialog!!.window!!.attributes

        (MainApplication.pixelRatio.screenWidth * widthRatio).toInt()
            .also { dialogParams.width = it }
        (MainApplication.pixelRatio.screenHeight * heightRatio).toInt()
            .also { dialogParams.height = it }

        dialogFragment.dialog!!.window!!.apply {
            attributes = dialogParams
        }
    }
}