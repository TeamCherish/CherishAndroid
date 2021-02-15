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
import com.sopt.cherish.ui.review.ReviseReviewFragment
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.FlexBoxExtension.addChipCalendar
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.detailPlantViewModel = viewModel
        initializeViewModelData()
        initializeCalendar(binding)
        observeCalendarModeChangeEvent(binding)
        observeCalendarData(binding)

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
        // binding 객체를 전역으로 만들어줘야하네....?
        // todo : bindingAdapdater를 어떻게 써야할 지는 다시한번 고민을 좀 해보는게 맞는거 같다.
        // todo : 바인딩 객체를 클래스필드 변수로 뺴는것이 아니라 바인딩 어댑터를 사용해서 좀 더 뷰 코드가 깔끔해 질 수 있도록 해보자.
        SimpleLogger.logI("CalendarFragment Resuming!")
        viewModel.fetchCalendarData()
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

    private fun observeCalendarModeChangeEvent(binding: FragmentCalendarBinding) {
        viewModel.calendarModeChangeEvent.observe(viewLifecycleOwner) {
            if (it) {
                binding.reviewBack.setImageResource(R.drawable.icn_allow)
                binding.calendarView.changeCalendarModeWeeks()
            } else {
                binding.reviewBack.setImageResource(R.drawable.icn_allow_top)
                binding.calendarView.changeCalendarModeMonths()
            }
        }
    }

    // todo : 여기 있는 모든 것들을 바인딩 어댑터로 만든다. today's work 02.15
    private fun initializeCalendar(binding: FragmentCalendarBinding) {
        allowCalendarCache(binding)
        changeCalendarMode(binding)
        addDateClickListener(binding)
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
            showDate(binding, date) // 바인딩 어댑터로 바꿀거야
            // 처음 캘린더 화면에 보여주기 위한 방법
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

    private fun observeCalendarData(binding: FragmentCalendarBinding) {
        viewModel.calendarData.observe(viewLifecycleOwner) { calendarRes ->
            // 물줘야 하는 날
            calendarRes.waterData.calendarData.forEach { calendarData ->
                binding.calendarView.addDecorator(
                    DotDecorator(
                        color = ContextCompat.getColor(requireContext(), R.color.cherish_green_sub),
                        dates = DateUtil.convertDateToCalendarDay(calendarData.wateredDate)
                    )
                )
            }
            // 물 준 날
            binding.calendarView.addDecorator(
                DotDecorator(
                    color = ContextCompat.getColor(requireContext(), R.color.cherish_pink_sub),
                    dates = DateUtil.convertDateToCalendarDay(calendarRes.waterData.futureWaterDate)
                )
            )
        }
    }

    // todo : 전부 바인딩 어댑터로 넘겨버리면 됨
    // todo : 바인딩 어댑터로 넘기게 되면 이제 캘린더 프래그먼트로 다시 돌아올 떄 데이터들이 다시금 세팅되어 있을 수 있음.
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