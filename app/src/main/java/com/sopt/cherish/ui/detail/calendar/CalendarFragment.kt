package com.sopt.cherish.ui.detail.calendar

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentCalendarBinding
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.ui.review.ReviseReviewFragment
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips
import com.sopt.cherish.util.extension.longToast

class CalendarFragment : Fragment() {

    private val viewModel: DetailPlantViewModel by activityViewModels()

    private lateinit var binding: FragmentCalendarBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.selectedMemoCalendarDay.value?.let {
            viewModel.selectedCalendarDay.value = it
        }
    }

    // todo : 메모를 클릭했을 떄는 calendar가 weekmode로 변경된상태에서 보여지게 하고
    // todo : 메모의 길이를 최대로 보여주세요!!! 라고 오퍼가 왔음
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
        observeSelectedMemoCalendarDay()
        initializeViewModelData()
        initializeCalendar()
        addDateClickListener()
        observeSelectedDay()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.isMemoClicked.observe(viewLifecycleOwner) {
            if (it)
                binding.calendarView.changeCalendarModeWeeks()
        }
    }

    private fun observeSelectedMemoCalendarDay() {
        viewModel.selectedMemoCalendarDay.observe(viewLifecycleOwner) {
            binding.calendarView.setDateSelected(it, true)
            viewModel.selectedCalendarDay.value = it
        }
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
        viewModel.fetchCalendarData()
        if (viewModel.isMemoClicked.value == false) {
            viewModel.selectedCalendarDay.value = viewModel.selectedCalendarDay.value
            binding.calendarView.removeDecorators()
            binding.calendarView.clearSelection()
            removeSelectedCalendarData()
            removeMemo(binding)
            removeSelectedDate(binding)
            viewModel.selectedCalendarDay.value = null
            binding.calendarViewChipLayout.clearChips()
            viewModel.selectedMemoCalendarDay.value = null
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

    private fun initializeViewModelData() {
        viewModel.calendarModeChangeEvent.value = false
    }

    private fun initializeCalendar() {
        changeCalendarMode(binding)
    }

    private fun changeCalendarMode(binding: FragmentCalendarBinding) {
        binding.reviewBack.setOnClickListener { view ->
            viewModel.calendarModeChangeEvent.value = !viewModel.calendarModeChangeEvent.value!!
        }
    }

    private fun observeSelectedDay() {
        viewModel.selectedCalendarDay.observe(viewLifecycleOwner) { selectedDay ->
            viewModel.selectedCalendarData.value = null
            viewModel.calendarData.value.let { calendarRes ->
                calendarRes?.waterData?.calendarData?.filter {
                    selectedDay == DateUtil.convertDateToCalendarDay(it.wateredDate)
                }?.map {
                    viewModel.selectedCalendarData.value = it
                }
            }
        }
    }

    private fun addDateClickListener() {
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            viewModel.selectedCalendarDay.value = date
        }
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
        viewModel.isMemoClicked.value = null
        removeSelectedCalendarData()
        removeMemo(binding)
        removeSelectedDate(binding)
        viewModel.selectedCalendarDay.value = null
        binding.calendarViewChipLayout.clearChips()
        viewModel.selectedMemoCalendarDay.value = null
    }

    companion object {
        const val TAG = "CalendarFragment"
    }
}