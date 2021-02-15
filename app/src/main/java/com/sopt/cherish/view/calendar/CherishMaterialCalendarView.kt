package com.sopt.cherish.view.calendar

import android.content.Context
import android.util.AttributeSet
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sopt.cherish.util.PixelUtil.dp

/**
 * Created on 01-06 by SSong-develop
 * default MaterialCalendarView height = 372dp
 */
class CherishMaterialCalendarView constructor(context: Context, attrs: AttributeSet) :
    MaterialCalendarView(context, attrs) {

    // todo :애뮬 상에서는 캘린더 줄어드는게 괜찮은데 , 실 기기에서는 잘 안줄어들음
    // 어떻게 해야할지 집에서 좀 고민해봐야겠다.
    fun changeCalendarModeWeeks() {
        state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit()
        // dp를 설정해서 하는건 그닥 좋지 못한 코딩방법
        layoutParams.height = 150.dp
    }

    // todo : 실기기로 할 때 dp값 수정해야함 , 좀 작은 느낌임
    fun changeCalendarModeMonths() {
        state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
        // dp를 설정해서 하는건 그닥 좋지 못한 코딩방법
        layoutParams.height = 388.dp
    }
}