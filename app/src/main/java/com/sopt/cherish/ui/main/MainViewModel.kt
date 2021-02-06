package com.sopt.cherish.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.repository.CalendarRepository
import com.sopt.cherish.repository.MainRepository
import com.sopt.cherish.repository.ReviewRepository
import com.sopt.cherish.repository.WateringRepository
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by SSong-develop on 2020-12-30
 */

class MainViewModel(
    private val mainRepository: MainRepository,
    private val wateringRepository: WateringRepository,
    private val reviewRepository: ReviewRepository,
    private val calendarRepository: CalendarRepository
) : ViewModel() {
    // [home] Server connection
    // userId는 값이 1개 , fetchUser 함수를 통해서 _users에 userId가 가지고 있는 cherish들이 보인다.
    // login 시 intent 에서 값을 받아서 옴

    // Utilities
    val animationTrigger = SingleLiveEvent<Boolean>()
    val exceptionLiveData = MutableLiveData<String>()

    // 로그인 하는 cherish를 이용하는 유저
    val userId = MutableLiveData<Int>()

    // 유저가 가지고 있는 cherish들
    val userNickName = MutableLiveData<String>()

    // Home에서 호출된 여러명
    private val _cherishUsers = MutableLiveData<UserResult>()
    val cherishUsers: MutableLiveData<UserResult>
        get() = _cherishUsers

    // recyclerview에 클릭된 유저 1명
    val selectedCherishUser = MutableLiveData<User>()

    fun fetchUsers() = viewModelScope.launch {
        try {
            _cherishUsers.postValue(mainRepository.fetchCherishUser(userId.value!!))
        } catch (exception: Exception) {
            exceptionLiveData.postValue(exception.message)
        }
    }

    // Contact Dialog
    private val _calendarData = MutableLiveData<CalendarRes>()
    val calendarData: MutableLiveData<CalendarRes>
        get() = _calendarData

    // userStatus Chip
    // todo : list로 표현하기 , 그 전에 이걸 뷰모델에서 가져도 되는건지?
    val userStatus1 = MutableLiveData<String>()
    val userStatus2 = MutableLiveData<String>()
    val userStatus3 = MutableLiveData<String>()

    fun fetchCalendarData() = viewModelScope.launch {
        try {
            _calendarData.postValue(selectedCherishUser.value?.id?.let {
                calendarRepository.getChipsData(
                    it
                )
            })
        } catch (exception: Exception) {
            exceptionLiveData.postValue(exception.message)
        }
    }

    // [Review] Server Connection done!
    fun sendReviewToServer(reviewWateringReq: ReviewWateringReq) = try {
        viewModelScope.launch {
            reviewRepository.sendReviewData(reviewWateringReq)
        }
    } catch (exception: Exception) {
        exceptionLiveData.postValue(exception.message)
    }

    // [DelayWatering] Server Connection done!
    private val today = DateUtil.convertDateToString(Calendar.getInstance().time)
    private val todayMonth = DateUtil.getMonth(today).toString()
    private val todayDay = DateUtil.getDay(today).toString()

    val delayWateringDateText = "${todayMonth}월${todayDay}일에 물주기"

    private val _postponeData = MutableLiveData<PostponeWateringRes>()
    val postponeData: MutableLiveData<PostponeWateringRes>
        get() = _postponeData

    fun getPostPoneWateringCount() = try {
        viewModelScope.launch {
            _postponeData.postValue(wateringRepository.getPostponeCount(selectedCherishUser.value?.id!!))
        }
    } catch (exception: Exception) {
        exceptionLiveData.postValue(exception.message)
    }


    fun postponeWateringDate(postponeWateringDateReq: PostponeWateringDateReq) = try {
        viewModelScope.launch {
            wateringRepository.postponeWateringDate(postponeWateringDateReq)
        }
    } catch (exception: Exception) {
        exceptionLiveData.postValue(exception.message)
    }

}