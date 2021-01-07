package com.sopt.cherish.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.sopt.cherish.ui.domain.CherryDataclass

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

    val dummyCherry = mutableListOf(
        CherryDataclass("1"),
        CherryDataclass("2"),
        CherryDataclass("3"),
        CherryDataclass("4"),
        CherryDataclass("5"),
        CherryDataclass("6"),
        CherryDataclass("7"),
        CherryDataclass("8"),
        CherryDataclass("9"),
        CherryDataclass("10"),
        CherryDataclass("11"),
        CherryDataclass("12"),
        CherryDataclass("13")
    )

    // homeFragment dummy Data
    val dummyRestDateData = "D-3"
    val dummyUserDescription = "아직 수명이 탄탄한"
    val dummyUserAffectionGauge = 70
    val dummyUserAffectionText = "70"
    val dummyUserCount = dummyCherry.size.toString()

}