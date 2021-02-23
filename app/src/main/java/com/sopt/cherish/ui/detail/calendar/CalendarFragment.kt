package com.sopt.cherish.ui.detail.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentCalendarBinding
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.ui.review.ReviseReviewFragment
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.FlexBoxExtension.addChipCalendar
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips
import com.sopt.cherish.util.extension.longToast

// todo : binding 객체 메모리 해제 꼭 시켜줘야 함
// todo : 삭제 하고 나서 다시 캘린더로 돌아왔을 때 텍스트가 refresh가 되지가 않는다 어떻게 해야하지
class CalendarFragment : Fragment() {

    private val viewModel: DetailPlantViewModel by activityViewModels()

    private lateinit var binding: FragmentCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.detailPlantViewModel = viewModel
        binding.calendarFragment = this

        initializeViewModelData()
        initializeCalendar(binding)
        addDateClickListener(binding)
        observeSelectedDay(binding)
        observeSelectedCalendarDay(binding)

        return binding.root
    }

    fun navigateReviseReview() {
        if (viewModel.selectedCalendarData.value != null) {
            parentFragmentManager.beginTransaction().addToBackStack(TAG)
                .replace(R.id.fragment_detail, ReviseReviewFragment()).commit()
        } else {
            longToast(requireContext(), "등록된 리뷰가 없어요 ㅠ")
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 캘린더")
        }
        // 이 2개는 맞음 해야하지 그치? 훈기야??
        viewModel.fetchCalendarData()
        binding.calendarView.removeDecorators()
        SimpleLogger.logI("onResume Start!!!")
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

    private fun initializeViewModelData() {
        viewModel.calendarModeChangeEvent.value = false
    }

    private fun initializeCalendar(binding: FragmentCalendarBinding) {
        changeCalendarMode(binding)
    }

    private fun changeCalendarMode(binding: FragmentCalendarBinding) {
        binding.reviewBack.setOnClickListener { view ->
            viewModel.calendarModeChangeEvent.value = !viewModel.calendarModeChangeEvent.value!!
        }
    }

    private fun observeSelectedDay(binding: FragmentCalendarBinding) {
        viewModel.selectedCalendarDay.observe(viewLifecycleOwner) { selectedDay ->
            // 선택된 날짜를 보여준다.
            selectedDay?.let { showDate(binding, it) }
            binding.calendarViewChipLayout.clearChips()
            removeMemo(binding)
            removeSelectedCalendarData()
            // calendarData의 value에서 filter와 map을 해 선택된 날짜의 calendarData가 있는지를 확인한다.
            viewModel.calendarData.value.let { calendarRes ->
                calendarRes?.waterData?.calendarData?.filter {
                    SimpleLogger.logI("$selectedDay ${it.wateredDate}")
                    selectedDay == DateUtil.convertDateToCalendarDay(it.wateredDate)
                }?.map {
                    viewModel.selectedCalendarData.value = it
                }
            }
        }
    }

    private fun observeSelectedCalendarDay(binding: FragmentCalendarBinding) {
        viewModel.selectedCalendarData.observe(viewLifecycleOwner) {
            showContent(binding)
        }
    }

    private fun addDateClickListener(binding: FragmentCalendarBinding) {
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            SimpleLogger.logI("$date")
            viewModel.selectedCalendarDay.value = date
        }
    }

    private fun showContent(binding: FragmentCalendarBinding) {
        viewModel.selectedCalendarData.value?.let {
            showChips(binding, listOf(it.userStatus1, it.userStatus2, it.userStatus3))
            showMemo(binding, it.review)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDate(binding: FragmentCalendarBinding, date: CalendarDay) {
        binding.calendarViewSelectedDate.text = "${date.year}년 ${date.month}월 ${date.day}일"
    }

    private fun showChips(binding: FragmentCalendarBinding, wateredChip: List<String?>) {
        binding.calendarViewChipLayout.clearChips()
        // todo : null일경우랑 아닌경우 혹은 그냥 공백으로 둔 경우를 모두 판단해야해서 보여줘야 하는 알고리즘을 작성해야합니다.
        wateredChip.forEach {
            it?.let { it1 -> binding.calendarViewChipLayout.addChipCalendar(it1) }
        }
    }

    private fun showMemo(binding: FragmentCalendarBinding, waterMemo: String) {
        binding.reviewAllText.text = waterMemo
    }

    private fun removeMemo(binding: FragmentCalendarBinding) {
        binding.reviewAllText.text = " "
    }

    private fun removeSelectedCalendarData() {
        viewModel.selectedCalendarData.value = null
    }

    private fun removeSelectedDate(binding: FragmentCalendarBinding) {
        binding.calendarViewSelectedDate.text = " "
    }

    override fun onDestroy() {
        super.onDestroy()
        removeSelectedCalendarData()
        removeMemo(binding)
        removeSelectedDate(binding)
        viewModel.selectedCalendarDay.value = null
        binding.calendarViewChipLayout.clearChips()
    }

    companion object {
        const val TAG = "CalendarFragment"
    }
}