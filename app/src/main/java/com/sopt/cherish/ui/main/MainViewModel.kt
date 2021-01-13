package com.sopt.cherish.ui.main

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.PostponeWateringDateReq
import com.sopt.cherish.remote.api.PostponeWateringRes
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.remote.api.UserResult
import com.sopt.cherish.repository.MainRepository
import com.sopt.cherish.ui.domain.CherryDataclass
import com.sopt.cherish.util.DateUtil
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val dummyLoginUserName = "또령"

    init {

    }

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

    // [home] Server connection
    val dummyUserId = 1 // login 시 서버에서 가져온 id 값을 그대로 사용하면 됨
    private val dummyCherishId = 1

    private val _users = MutableLiveData<UserResult>()
    val users: MutableLiveData<UserResult>
        get() = _users

    fun fetchUsers() = viewModelScope.launch {
        _users.postValue(mainRepository.fetchCherishUser(dummyUserId))
    }

    // [MyPage] Server connection it has an error!~!!
/*    lateinit var myPageUserData : MyPageUserRes
    private fun fetchMyPageUserData() = viewModelScope.launch {
        myPageUserData = mainRepository.fetchCherishUserPageData(dummyUserId)
    }*/

    // [Review] Server Connection
    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        mainRepository.sendReviewData(reviewWateringReq)
    }

    // [DelayWatering] Server Connection
    private val today = DateUtil.convertDateToString(Calendar.getInstance().time)
    private val todayMonth = DateUtil.getMonth(today).toString()
    private val todayDay = DateUtil.getDay(today).toString()

    val delayWateringDateText = "${todayMonth}월${todayDay}일에 물주기"

    private val _postponeData = MutableLiveData<PostponeWateringRes>()
    val postponeData: MutableLiveData<PostponeWateringRes>
        get() = _postponeData

    fun getPostPoneWateringCount() = viewModelScope.launch {
        _postponeData.postValue(mainRepository.getPostponeCount(dummyCherishId))
    }

    fun postponeWateringDate(postponeWateringDateReq: PostponeWateringDateReq) =
        viewModelScope.launch {
            mainRepository.postponeWateringDate(postponeWateringDateReq)
        }
}