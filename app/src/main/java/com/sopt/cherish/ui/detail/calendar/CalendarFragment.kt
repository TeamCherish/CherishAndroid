package com.sopt.cherish.ui.detail.calendar

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
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.extension.FlexBoxExtension.addChipCalendar
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips
import com.sopt.cherish.util.extension.shortToast
import com.sopt.cherish.view.calendar.DotDecorator

class CalendarFragment : Fragment() {

    private val viewModel: DetailPlantViewModel by activityViewModels()

    // todo : SingleLiveEvent 공부하기
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding: FragmentCalendarBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

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
        menu.getItem(2).isVisible = false // invisible menuitem 2

        (activity as DetailPlantActivity).invalidateOptionsMenu()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeCalendar(binding: FragmentCalendarBinding) {
        allowCalendarCache(binding)
        reviseNotes(binding)
        changeCalendarMode(binding)
        addDecorator(binding)
        addDateClickListener(binding)
    }

    private fun changeCalendarMode(binding: FragmentCalendarBinding) {
        binding.reviewBack.setOnClickListener { view ->
            // 클릭 시 화살표의 모양이 왔다갔다 하면서 바뀌도록 하면 됨
            // review button 이 눌림에 따라
            // textview의 ellipsize 와 maxLine의 수를 바꿔주면 된다.
            // SingleLiveData로 제어를 해야하는데 지금 귀찮음 나중에 하자
            if (viewModel.calendarAllowChange) {
                viewModel.calendarAllowChange = false
                binding.reviewBack.setImageResource(R.drawable.icn_allow)
                binding.calendarView.changeCalendarModeWeeks()
            } else {
                viewModel.calendarAllowChange = true
                binding.reviewBack.setImageResource(R.drawable.icn_allow_top)
                binding.calendarView.changeCalendarModeMonths()
            }
        }
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
                    colorGreenSub,
                    DateUtil.convertDateToCalendarDay(it.waterData.futureWaterDate)
                )
            )
            it.waterData.calendarData.forEach {
                binding.calendarView.addDecorator(
                    DotDecorator(
                        colorPinkSub,
                        DateUtil.convertDateToCalendarDay(it.wateredDate)
                    )
                )
            }
        }
    }

    // 메모 수정
    private fun reviseNotes(binding: FragmentCalendarBinding) {
        binding.calendarViewMemoReviseBtn.setOnClickListener { view ->
            // 2순위라서 구현 안해~
            shortToast(requireContext(), "아직 미구현")
        }
    }

    private fun addDateClickListener(binding: FragmentCalendarBinding) {
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            showDate(binding, date)
            binding.calendarViewChipLayout.clearChips()
            viewModel.calendarData.observe(viewLifecycleOwner) { calendarRes ->
                val wateredDayList = mutableListOf<CalendarDay?>()
                val waterDayMemoList = mutableListOf<String?>()
                val waterDayChipList = mutableListOf<List<String?>>()

                calendarRes.waterData.calendarData.forEach { calendarData ->
                    wateredDayList.add(DateUtil.convertDateToCalendarDay(calendarData.wateredDate))
                    waterDayMemoList.add(calendarData.review)
                    waterDayChipList.add(
                        listOf(
                            calendarData.userStatus1,
                            calendarData.userStatus2,
                            calendarData.userStatus3
                        )
                    )
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
            it?.let { it1 -> binding.calendarViewChipLayout.addChipCalendar(it1) }
        }
    }

    private fun showMemo(binding: FragmentCalendarBinding, waterMemo: String) {
        binding.reviewAllText.text = " "
        binding.reviewAllText.text = waterMemo
    }
}