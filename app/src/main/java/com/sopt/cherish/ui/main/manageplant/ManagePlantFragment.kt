package com.sopt.cherish.ui.main.manageplant

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.sopt.cherish.remote.api.MyPageUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 식물 보관함 뷰
 */
class ManagePlantFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var tabIndex: Int = 0
    private var isCollapsed: Boolean = true
    private val requestData = RetrofitBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentManagePlantBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_manage_plant, container, false)

        initializeTabLayoutView(binding)
        initializeBottomSheetBehavior(binding)
        initializeServerRequest()

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

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (tabIndex == 0) {
                    binding.myPageResetBtn.visibility = View.GONE
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        binding.myPageAddPlantBtn.visibility = View.VISIBLE
                        isCollapsed = false
                    }

                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        binding.myPageAddPlantBtn.visibility = View.GONE
                        isCollapsed = true
                    }

                }
                if (tabIndex == 1) {
                    binding.myPageAddPlantBtn.visibility = View.GONE
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        binding.myPageResetBtn.visibility = View.VISIBLE
                        isCollapsed = false
                    }


                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        binding.myPageResetBtn.visibility = View.GONE
                        isCollapsed = true
                    }

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
                tabIndex = binding.myPageBottomTab.selectedTabPosition

                if (tabIndex == 0) {
                    binding.myPageResetBtn.visibility = View.GONE
                    if (isCollapsed) {
                        binding.myPageAddPlantBtn.visibility = View.GONE
                    } else
                        binding.myPageAddPlantBtn.visibility = View.VISIBLE
                }
                if (tabIndex == 1) {
                    binding.myPageAddPlantBtn.visibility = View.GONE
                    if (isCollapsed) {
                        binding.myPageResetBtn.visibility = View.GONE
                    } else {
                        binding.myPageResetBtn.visibility = View.VISIBLE
                    }
                }

                (activity as MainActivity).replaceFragment(tabIndex)
            }
        })

    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        startActivity(intent)
    }

    private fun initializeServerRequest() {

        requestData.myPageAPI.fetchUserPage(1)
            .enqueue(
                object : Callback<MyPageUserRes> {
                    override fun onFailure(call: Call<MyPageUserRes>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<MyPageUserRes>,
                        response: Response<MyPageUserRes>
                    ) {
                        Log.d("success", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->
                                Log.d("data success!", it.myPageUserData.waterCount.toString())
                            }
                    }
                }
            )
    }

}