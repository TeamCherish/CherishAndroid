package com.sopt.cherish.util.extension

import android.content.Context
import android.widget.Toast

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