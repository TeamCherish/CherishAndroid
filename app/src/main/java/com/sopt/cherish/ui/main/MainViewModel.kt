package com.sopt.cherish.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel : ViewModel() {
    val dummyUserName = "남쿵둥이와는"
    val dummyUserStatus = arrayListOf<String>("생일", "취업준비중", "헤어짐")
    val dummyUserPhoneNumber: Uri = Uri.parse("tel:010-2563-9702")
    val dummyUserMessageNumber: Uri = Uri.parse("smsto:010-2563-9702")

}