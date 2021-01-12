package com.sopt.cherish.ui.datail.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentCalendarBinding
import com.sopt.cherish.ui.datail.DetailPlantActivity
import com.sopt.cherish.ui.datail.DetailPlantViewModel
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips
import com.sopt.cherish.util.extension.showKeyboard
import com.sopt.cherish.view.calendar.DotDecorator

class CalendarFragment : Fragment() {
    private val viewModel: DetailPlantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding: FragmentCalendarBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        // kotlin에서 받을 때 DateFormat으로 받기
        viewModel.fetchCalendarData()
        binding.detailPlantViewModel = viewModel


        initializeCalendar(binding)

        // viewModel 작업

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 캘린더")

        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.getItem(0).isVisible = false //disable menuitem 5
        menu.getItem(1).isVisible = false // invisible menuitem 2
        (activity as DetailPlantActivity).invalidateOptionsMenu()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.onBackPressed()

            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeCalendar(binding: FragmentCalendarBinding) {
        allowCalendarCache(binding)
        takeNotes(binding)
        addDecorator(binding)
        addDateClickListener(binding)
    }

    private fun allowCalendarCache(binding: FragmentCalendarBinding) {
        binding.calendarView.state().edit().isCacheCalendarPositionEnabled(true)
    }

    private fun addDecorator(binding: FragmentCalendarBinding) {
        val colorPinkSub = ContextCompat.getColor(requireContext(), R.color.cherish_green_sub)
        val colorGreenSub = ContextCompat.getColor(requireContext(), R.color.cherish_pink_sub)

        viewModel.dummyNeedWateringListDay.forEach {
            binding.calendarView.addDecorator(DotDecorator(colorGreenSub, it))
        }
        viewModel.dummyWateringListDay.forEach {
            binding.calendarView.addDecorator(DotDecorator(colorPinkSub, it))
        }
    }

    private fun takeNotes(binding: FragmentCalendarBinding) {
        // 메모를 할 수 있게하다 라는 뜻을 원하는데 마땅히 떠오르는게 없어서 일단 이케 적음
        binding.calendarViewMemoCreateBtn.setOnClickListener { view ->
            view.showKeyboard()
            binding.calendarView.changeCalendarModeWeeks()
        }
    }

    private fun addDateClickListener(binding: FragmentCalendarBinding) {
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            showDate(binding, date)
/*            showMemo(binding)
            showChips(binding)*/
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDate(binding: FragmentCalendarBinding, date: CalendarDay) {
        binding.calendarViewSelectedDate.text = "${date.year}년 ${date.month}월 ${date.day}일"
    }

    private fun showChips(binding: FragmentCalendarBinding) {
        binding.calendarViewChipLayout.clearChips()
        viewModel.dummyChips.forEach {
            binding.calendarViewChipLayout.addChip(it)
        }
    }

    private fun showMemo(binding: FragmentCalendarBinding) {
        binding.reviewAllText.text = " "
        binding.reviewAllText.text = viewModel.dummyMemo
    }
}