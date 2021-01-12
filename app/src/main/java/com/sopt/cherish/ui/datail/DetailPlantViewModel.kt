package com.sopt.cherish.ui.datail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sopt.cherish.remote.api.CalendarRes
import com.sopt.cherish.repository.DetailPlantRepository
import kotlinx.coroutines.launch

class DetailPlantViewModel(
    private val detailPlantRepository: DetailPlantRepository
) : ViewModel() {
    val dummyWateringListDay = listOf(
        CalendarDay.from(2021, 1, 10),
        CalendarDay.from(2021, 1, 13)
    )
    val dummyNeedWateringListDay = listOf(
        CalendarDay.from(2021, 1, 15),
        CalendarDay.from(2021, 1, 20)
    )

    val dummyMemo = "안녕 나는 우스꽝스러운 광대야 내가 광대인 이유는 앞으로 차차 얘기할게 잘 들어줬으면 좋겠다."

    val dummyChips = listOf(
        "인생이란", "왜 항상 배고픈걸까", "곱창먹고싶다"
    )

    // Keyword , Review 조회하기
    private val dummyCherishId = 1

    private val _calendarData = MutableLiveData<CalendarRes>()
    val calendarData: MutableLiveData<CalendarRes>
        get() = _calendarData

    fun fetchCalendarData() = viewModelScope.launch {
        _calendarData.postValue(detailPlantRepository.fetchCalendarData(dummyCherishId))
    }

}