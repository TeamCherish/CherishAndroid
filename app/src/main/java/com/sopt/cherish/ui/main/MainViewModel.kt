package com.sopt.cherish.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel : ViewModel() {
    private val dummyLoginUserName = "또령"

    val dummyUserName = "남쿵둥이"
    val contactDummyUserName = "${dummyUserName}와는"

    val reviewDescription = "${dummyUserName}과의 물주기를 기록해주세요"
    val reviewText = "${dummyLoginUserName}님! ${dummyUserName}님과의"

    val dummyUserStatus = arrayListOf<String>("생일", "취업준비중", "헤어짐")
    val dummyUserPhoneNumber: Uri = Uri.parse("tel:010-2563-9702")
    val dummyUserMessageNumber: Uri = Uri.parse("smsto:010-2563-9702")
}