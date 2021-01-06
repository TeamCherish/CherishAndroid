package com.sopt.cherish.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentCalendarBinding
import com.sopt.cherish.util.extension.hideKeyboard
import com.sopt.cherish.util.extension.showKeyboard
import com.sopt.cherish.view.calendar.DotDecorator

class CalendarFragment : Fragment() {
    // ViewModel 로 넘겨버릴겁니다.
    private val dummyWateringListDay = listOf(
        CalendarDay.from(2021, 1, 10),
        CalendarDay.from(2021, 1, 13)
    )
    private val dummyNeedWateringListDay = listOf(
        CalendarDay.from(2021, 1, 15),
        CalendarDay.from(2021, 1, 20)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCalendarBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

        initializeCalendar(binding)
        return binding.root
    }

    private fun initializeCalendar(binding: FragmentCalendarBinding) {
        // need 서버에서 데이터 가져온 거 check

        // Color value 또한
        val colorPinkSub = ContextCompat.getColor(requireContext(), R.color.cherish_green_sub)
        val colorGreenSub = ContextCompat.getColor(requireContext(), R.color.cherish_pink_sub)

        // Cache 허용
        binding.calendarView.state().edit().isCacheCalendarPositionEnabled(true)

        // addMemo function
        binding.calendarViewMemoCreateBtn.setOnClickListener { view ->
            view.showKeyboard()
            binding.calendarView.changeCalendarModeWeeks()
        }
        binding.testImageBtn.setOnClickListener { view ->
            view.hideKeyboard()
            binding.calendarView.changeCalendarModeMonths()
        }

        // decorate
        dummyNeedWateringListDay.forEach {
            binding.calendarView.addDecorator(DotDecorator(colorGreenSub, it))
        }
        dummyWateringListDay.forEach {
            binding.calendarView.addDecorator(DotDecorator(colorPinkSub, it))
        }

        // date ClickListener
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            // widget : materialcalendar에 대한 widget이 그냥 넘어오는 것 같다. 자세히는 모름
            // date : 선택된 날짜
            // selected : boolean 값으로 select가 되었는지 안되었는지를 판단하게 해주는 값

            // 선택되면 그에 따라서 메모가 나온다던지 ~ 그런기능을 해주면 될 거 같고
            showDate(binding, date)
            showMemo(binding)
            showChips(binding)
        }
    }

    private fun showDate(binding: FragmentCalendarBinding, date: CalendarDay) {
        binding.calendarViewSelectedDate.text = "${date.year}년 ${date.month}월 ${date.day}일"
    }

    private fun showChips(binding: FragmentCalendarBinding) {
        // dummy Data로 구현하면 될 듯
    }

    private fun showMemo(binding: FragmentCalendarBinding) {

    }

}