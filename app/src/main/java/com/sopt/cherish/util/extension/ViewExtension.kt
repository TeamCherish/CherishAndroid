package com.sopt.cherish.util.extension

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginTop

/**
 * Created on 2021-1-1 by SSong-develop
 * Toast같은 debug함수들 쉽게 사용할 수 있는 확장함수들
 */

fun shortToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun longToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun View.showKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).also {
        it.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).also {
        it.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun TextView.setMaxLinesToEllipsize() = doOnLayout {
    val numberOfCompletelyVisibleLines = (measuredHeight - marginTop - marginBottom) / lineHeight
    maxLines = numberOfCompletelyVisibleLines
    ellipsize = TextUtils.TruncateAt.END
}