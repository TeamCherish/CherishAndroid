package com.sopt.cherish.view.calendar

import android.content.Context
import android.util.AttributeSet
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sopt.cherish.util.PixelUtil.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created on 01-06 by SSong-develop
 * default MaterialCalendarView height = 372dp
 * todo : 캘린더 motionLayout으로 줄어드는거 확실하게 표현해주기
 */
class CherishMaterialCalendarView constructor(context: Context, attrs: AttributeSet) :
    MaterialCalendarView(context, attrs) {

    // todo :애뮬 상에서는 캘린더 줄어드는게 괜찮은데 , 실 기기에서는 잘 안줄어들음
    fun changeCalendarModeWeeks() {
        CoroutineScope(Dispatchers.Main).launch {
            state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit()
            // dp를 설정해서 하는건 그닥 좋지 못한 코딩방법
            layoutParams.height = 150.dp
        }
    }

    // todo : 실기기로 할 때 dp값 수정해야함 , 좀 작은 느낌임
    fun changeCalendarModeMonths() {
        CoroutineScope(Dispatchers.Main).launch {
            state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
            // dp를 설정해서 하는건 그닥 좋지 못한 코딩방법
            layoutParams.height = 388.dp
        }
    }
}