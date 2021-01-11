package com.sopt.cherish.ui.main.manageplant

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentManagePlantBinding
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.MainViewModel

/**
 * 식물 보관함 뷰
 */
class ManagePlantFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentManagePlantBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_manage_plant, container, false)

        initializeTabLayoutView(binding)
        initializeBottomSheetBehavior(binding)

        binding.myPageAddPlantBtn.setOnClickListener {
            navigatePhoneBook()
        }

        return binding.root
    }

    private fun initializeBottomSheetBehavior(binding: FragmentManagePlantBinding) {
        val standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        // bottom sheet state 지정
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 1600
        standardBottomSheetBehavior.expandedOffset = 280
        standardBottomSheetBehavior.isHideable = false

        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            val tabIndex = binding.myPageBottomTab.selectedTabPosition

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (tabIndex == 0) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED)
                        binding.myPageAddPlantBtn.visibility = View.VISIBLE

                    if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                        binding.myPageAddPlantBtn.visibility = View.GONE
                }
                if (tabIndex == 1) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED)
                        binding.myPageResetBtn.visibility = View.VISIBLE

                    if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                        binding.myPageResetBtn.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun initializeTabLayoutView(binding: FragmentManagePlantBinding) {

        /**
        todo: 식물 5 처럼 텍스트 분리해서 탭 지정
        연락처 탭 클릭시 상단 탭바 변경되어야 함
         */
        binding.myPageBottomTab.addTab(binding.myPageBottomTab.newTab().setText("식물"))
        binding.myPageBottomTab.addTab(binding.myPageBottomTab.newTab().setText("연락처"))

        for (i in 0 until binding.myPageBottomTab.tabCount) {
            val tab = (binding.myPageBottomTab.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(24, 0, 0, 0)
            tab.requestLayout()
        }

        activity?.supportFragmentManager!!.beginTransaction()
            .add(R.id.my_page_bottom_container, PlantFragment()).commit()

        binding.myPageBottomTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIndex = binding.myPageBottomTab.selectedTabPosition

                (activity as MainActivity).replaceFragment(tabIndex)
            }
        })

    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        startActivity(intent)
    }

}