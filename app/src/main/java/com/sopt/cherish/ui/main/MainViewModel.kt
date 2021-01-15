package com.sopt.cherish.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.R
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.MainRepository
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SimpleLogger
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    // [home] Server connection
    // userId는 값이 1개 , fetchUser 함수를 통해서 _users에 userId가 가지고 있는 cherish들이 보인다.
    // login 시 intent 에서 값을 받아서 옴

    // Gif Uri
    val normalFlowerAnimationUri = R.raw.mindle_flower_android
    val wateringFlowerAnimationUri = R.raw.watering_min_android
    val witherFlowerAnimationUri = R.raw.wither_min_android

    // 로그인 하는 cherish를 이용하는 유저
    val userId = MutableLiveData<Int>()

    // 유저가 가지고 있는 cherish들
    val userNickName = MutableLiveData<String>()

    // main Home에서 호출된 여러명
    private val _cherishUsers = MutableLiveData<UserResult>()
    val cherishUsers: MutableLiveData<UserResult>
        get() = _cherishUsers

    // recyclerview에 클릭된 유저 1명
    val cherishUser = MutableLiveData<User>()

    fun fetchUsers() = viewModelScope.launch {
        try {
            _cherishUsers.postValue(mainRepository.fetchCherishUser(userId.value!!))
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    // Contact Dialog
    private val _calendarData = MutableLiveData<CalendarRes>()
    val calendarData: MutableLiveData<CalendarRes>
        get() = _calendarData

    // userStatus Chip
    val userStatus1 = MutableLiveData<String>()
    val userStatus2 = MutableLiveData<String>()
    val userStatus3 = MutableLiveData<String>()

    fun fetchCalendarData() = viewModelScope.launch {
        _calendarData.postValue(cherishUser.value?.id?.let {
            mainRepository.getChipsData(
                it
            )
        })
    }

    // [Review] Server Connection done!
    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = viewModelScope.launch {
        mainRepository.sendReviewData(reviewWateringReq)
        SimpleLogger.logI(mainRepository.sendReviewData(reviewWateringReq).reviewScore.toString())
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
        _postponeData.postValue(mainRepository.getPostponeCount(cherishUser.value?.id!!))
    }

    fun postponeWateringDate(postponeWateringDateReq: PostponeWateringDateReq) =
        viewModelScope.launch {
            mainRepository.postponeWateringDate(postponeWateringDateReq)
        }

    override fun onCleared() {
        onCleared()
    }
}