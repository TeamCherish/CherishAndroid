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
import com.sopt.cherish.util.extension.FlexBoxExtension.addChipCalendar
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips

// todo : 삭제하고 왔을 때 갱신은 되는데 dot가 안지워짐
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
        initializeViewModelData()
        initializeCalendar(binding)
        addDateClickListener(binding)

        binding.calendarViewMemoReviseBtn.setOnClickListener {
            navigateReviseReview()
        }

        return binding.root
    }

    private fun navigateReviseReview() {
        parentFragmentManager.beginTransaction().addToBackStack(TAG)
            .replace(R.id.fragment_detail, ReviseReviewFragment()).commit()
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 캘린더")
        }
        viewModel.fetchCalendarData()
        binding.calendarViewChipLayout.clearChips()
        binding.calendarView.selectedDate?.let { showDate(binding, it) }
        binding.calendarView.selectedDate?.let { showContent(binding, it) }
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
        allowCalendarCache(binding)
        changeCalendarMode(binding)
    }

    private fun changeCalendarMode(binding: FragmentCalendarBinding) {
        binding.reviewBack.setOnClickListener { view ->
            viewModel.calendarModeChangeEvent.value = !viewModel.calendarModeChangeEvent.value!!
        }
    }

    private fun allowCalendarCache(binding: FragmentCalendarBinding) {
        binding.calendarView.state().edit().isCacheCalendarPositionEnabled(true)
    }

    private fun addDateClickListener(binding: FragmentCalendarBinding) {

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            binding.calendarViewChipLayout.clearChips()
            showDate(binding, date)
            showContent(binding, selectedDate = date)
        }
    }

    private fun showContent(binding: FragmentCalendarBinding, selectedDate: CalendarDay) {
        viewModel.calendarData.value.let { calendarRes ->
            binding.reviewAllText.text = " "
            calendarRes?.waterData?.calendarData?.filter {
                DateUtil.convertDateToCalendarDay(it.wateredDate) == selectedDate
            }?.map {
                showChips(binding, listOf(it.userStatus1, it.userStatus2, it.userStatus3))
                showMemo(binding, it.review)
                viewModel.selectedCalendarData.value = it
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

    companion object {
        const val TAG = "CalendarFragment"
    }
}