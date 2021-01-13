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
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips
import com.sopt.cherish.view.calendar.DotDecorator

class CalendarFragment : Fragment() {
    private val viewModel: DetailPlantViewModel by activityViewModels()

    // keyboard Boolean 디자이너와 얘기 끝나면 제대로 정해서 할 예정
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding: FragmentCalendarBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

        // viewModel 작업
        viewModel.fetchCalendarData()
        binding.detailPlantViewModel = viewModel
        initializeCalendar(binding)


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
        viewModel.calendarData.observe(viewLifecycleOwner) {
            binding.calendarView.addDecorator(
                DotDecorator(
                    colorPinkSub,
                    DateUtil.convertDateToCalendarDay(it.waterData.futureWaterDate)
                )
            )
            it.waterData.calendarData.forEach {
                binding.calendarView.addDecorator(
                    DotDecorator(
                        colorGreenSub,
                        DateUtil.convertDateToCalendarDay(it.wateredDate)
                    )
                )
            }
        }
    }

    private fun takeNotes(binding: FragmentCalendarBinding) {
        // 메모를 할 수 있게하다 라는 뜻을 원하는데 마땅히 떠오르는게 없어서 일단 이케 적음
        binding.calendarViewMemoCreateBtn.setOnClickListener { view ->
            binding.calendarView.changeCalendarModeMonths()
        }
        binding.reviewBack.setOnClickListener { view ->
            // 클릭 시 화살표의 모양이 왔다갔다 하면서 바뀌도록 하면 됨
            // review button 이 눌림에 따라
            // textview의 ellipsize 와 maxLine의 수를 바꿔주면 된다.
            binding.calendarView.changeCalendarModeWeeks()
        }
    }

    private fun addDateClickListener(binding: FragmentCalendarBinding) {
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            showDate(binding, date)
            binding.calendarViewChipLayout.clearChips()
            viewModel.calendarData.observe(viewLifecycleOwner) {
                val wateredDayList = mutableListOf<CalendarDay?>()
                val waterDayMemoList = mutableListOf<String?>()
                val waterDayChipList = mutableListOf<List<String?>>()
                it.waterData.calendarData.forEach {
                    wateredDayList.add(DateUtil.convertDateToCalendarDay(it.wateredDate))
                    waterDayMemoList.add(it.review)
                    waterDayChipList.add(listOf(it.userStatus1, it.userStatus2, it.userStatus3))
                }
                // 함수화 해야합니다.
                for (i in 0 until wateredDayList.size) {
                    if (wateredDayList[i] == date) {
                        waterDayMemoList[i]?.let { it1 -> showMemo(binding, it1) }
                        showChips(binding, waterDayChipList[i])
                        break
                    } else {
                        binding.reviewAllText.text = " "
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDate(binding: FragmentCalendarBinding, date: CalendarDay) {
        binding.calendarViewSelectedDate.text = "${date.year}년 ${date.month}월 ${date.day}일"
    }

    private fun showChips(binding: FragmentCalendarBinding, wateredChip: List<String?>) {
        binding.calendarViewChipLayout.clearChips()
        wateredChip.forEach {
            it?.let { it1 -> binding.calendarViewChipLayout.addChip(it1) }
        }
    }

    private fun showMemo(binding: FragmentCalendarBinding, waterMemo: String) {
        binding.reviewAllText.text = " "
        binding.reviewAllText.text = waterMemo
    }
}