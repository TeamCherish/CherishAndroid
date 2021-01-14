package com.sopt.cherish.ui.main

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.MainRepository
import com.sopt.cherish.util.DateUtil
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    // todo : 서버통신을 한번만 하도록 주기를 설정해줘야함
    // todo : review페이지를 넘어갈 떄 viewModel이 새로 생성되는게 문제임
    val dummyUserName = "남쿵둥이"
    val contactDummyUserName = "${dummyUserName}와는"

    // 실 기기가 공기계라서 전화랑 메시지는 불가능 하다. 카카오톡은 켜짐!
    // 이것도 바꿔야함
    val dummyUserStatus = arrayListOf<String>("생일", "취업준비중", "헤어짐")
    val dummyUserPhoneNumber: Uri = Uri.parse("tel:010-2563-9702")
    val dummyUserMessageNumber: Uri = Uri.parse("smsto:010-2563-9702")

    // homeFragment dummy Data
    val dummyRestDateData = "D-3"
    val dummyUserDescription = "아직 수명이 탄탄한"
    val dummyUserAffectionGauge = 70
    val dummyUserAffectionText = "70"

    // [home] Server connection
    // userId는 값이 1개 , fetchUser 함수를 통해서 _users에 userId가 가지고 있는 cherish들이 보인다.

    // login 시 intent 에서 값을 받아서 옴
    // 로그인 하는 cherish를 이용하는 유저
    val userId = MutableLiveData<Int>()

    // 유저가 가지고 있는 cherish들
    val userNickName = MutableLiveData<String>()

    // 선택된 경우 cherishId가 변경됨
    private val dummyCherishId = 1

    // main Home에서 호출된 여러명
    private val _cherishUsers = MutableLiveData<UserResult>()
    val cherishUsers: MutableLiveData<UserResult>
        get() = _cherishUsers

    // recyclerview에 클릭된 유저 1명
    val cherishUser = MutableLiveData<User>()

    private val _cherishUserId = MutableLiveData<Int>()
    val cherishUserId: MutableLiveData<Int>
        get() = _cherishUserId

    val reviewText = "${userNickName.value}님! ${cherishUser.value?.nickName}님과의"

    val reviewDescription = "${cherishUser.value?.nickName}과의 물주기를 기록하세요"
    fun fetchUsers() = viewModelScope.launch {
        try {
            _cherishUsers.postValue(mainRepository.fetchCherishUser(userId.value!!))
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }


    // [Review] Server Connection done!
    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        mainRepository.sendReviewData(reviewWateringReq)
    }

    // [DelayWatering] Server Connection done!
    val today = DateUtil.convertDateToString(Calendar.getInstance().time)
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