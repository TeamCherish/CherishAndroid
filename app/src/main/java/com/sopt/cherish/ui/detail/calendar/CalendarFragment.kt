package com.sopt.cherish.ui.detail.calendar

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

// todo : binding 객체 메모리 해제 꼭 시켜줘야 함
// todo : 삭제 하고 나서 다시 캘린더로 돌아왔을 때 텍스트가 refresh가 되지가 않는다 어떻게 해야하지
// todo : 삭제 혹은 수정을 했을 때 내용들이 갱신이 되려면 observing이 되어있어야 한다 그러므로 liveData로 되어 있어야 한다.
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
        observeSelectedDay()

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
        viewModel.fetchCalendarData()
        binding.calendarView.removeDecorators()
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
            if (viewModel.calendarModeChangeEvent.value!!) {
                binding.reviewAllText.maxLines = 5
            } else {
                binding.reviewAllText.maxLines = 2
            }
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

    private fun addDateClickListener(binding: FragmentCalendarBinding) {
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