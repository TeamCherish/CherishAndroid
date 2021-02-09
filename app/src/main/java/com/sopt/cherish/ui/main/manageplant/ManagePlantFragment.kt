package com.sopt.cherish.ui.main.manageplant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentManagePlantBinding
import com.sopt.cherish.databinding.MyPageCustomTabBinding
import com.sopt.cherish.remote.api.MyPageUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.adapter.MyPageBottomSheetAdapter
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.PixelUtil.dp
import com.sopt.cherish.util.SimpleLogger
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
    private lateinit var tabView:View
    private lateinit var tabBinding:MyPageCustomTabBinding

    //private lateinit var myPageBottomSheetAdapter:MyPageBottomSheetAdapter

    lateinit var binding: FragmentManagePlantBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_manage_plant, container, false)
        tabBinding=MyPageCustomTabBinding.inflate(inflater,container,false)

        //binding.editSearch.textCh
        // 예진이 userId , viewModel.userId.value 라고하면 userId 찾을 수 있어요
        SimpleLogger.logI(viewModel.userId.value.toString())
        initializeServerRequest(binding)
        initializeTabLayoutView(binding)
        initializeBottomSheetBehavior(binding)
        binding.myPageAddPlantBtn.setOnClickListener {
            navigatePhoneBook()
        }

        return binding.root
    }
/*
    override fun onResume() {
        super.onResume()
        myPageBottomSheetAdapter.notifyDataSetChanged()
    } */


    private fun initializeBottomSheetBehavior(binding: FragmentManagePlantBinding) {
        val standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeStandardBottomSheet)
        // bottom sheet state 지정
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 350.dp
        standardBottomSheetBehavior.expandedOffset = 68.dp
        standardBottomSheetBehavior.isHideable = false

        //검색 버튼 눌렀을 때
        binding.searchBox.setOnClickListener {
            binding.searchBg.visibility = View.VISIBLE
            binding.cancelText.visibility = View.VISIBLE

            binding.myPageText.visibility = View.INVISIBLE
            binding.searchBox.visibility = View.INVISIBLE
        }

        //취소 눌렀을 때
        binding.cancelText.setOnClickListener{
            binding.searchBg.visibility = View.INVISIBLE
            binding.cancelText.visibility = View.INVISIBLE

            binding.myPageText.visibility = View.VISIBLE
            binding.searchBox.visibility = View.VISIBLE
        }

        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (tabIndex == 0) { //식물 탭 클릭 시

                    if (newState == BottomSheetBehavior.STATE_EXPANDED) { //바텀시트 확장됐을 경우
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )

                        binding.myPageAddPlantBtn.visibility = View.VISIBLE //식물 추가 visible
                        isCollapsed = false
                    }

                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) { //바텀시트 축소됐을 경우
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )

                        binding.myPageAddPlantBtn.visibility = View.INVISIBLE //식물 추가 invisible
                        isCollapsed = true
                    }

                }
                if (tabIndex == 1) { //연락처 탭 클릭 시
                    binding.myPageAddPlantBtn.visibility = View.INVISIBLE

                    if (newState == BottomSheetBehavior.STATE_EXPANDED) { //바텀시트 확장됐을 경우
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )
                        isCollapsed = false
                    }


                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )

                        isCollapsed = true
                    }

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun initializeTabLayoutView(binding: FragmentManagePlantBinding) {


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

                    if (isCollapsed) {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )
                        binding.myPageAddPlantBtn.visibility = View.INVISIBLE
                    } else {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )
                        binding.myPageAddPlantBtn.visibility = View.VISIBLE
                    }

                }
                if (tabIndex == 1) {
                    binding.myPageAddPlantBtn.visibility = View.INVISIBLE
                    if (isCollapsed) {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )

                    } else {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )

                    }
                }

                (activity as MainActivity).replaceFragment(tabIndex)
            }
        })

    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.userId.value!!)
        startActivity(intent)
    }


    private fun initializeServerRequest(binding: FragmentManagePlantBinding) {


        requestData.myPageAPI.fetchUserPage(viewModel.userId.value!!)
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

                                binding.myPageWateringCnt.text =
                                    it.myPageUserData.waterCount.toString()
                                binding.myPagePostponeCnt.text =
                                    it.myPageUserData.postponeCount.toString()
                                binding.myPageFinishCnt.text =
                                    it.myPageUserData.completeCount.toString()
                                binding.myPageUserName.text=it.myPageUserData.user_nickname


                                //val tabText = "식물 " + it.myPageUserData.totalCherish.toString()
                                //tabView= layoutInflater.inflate(R.layout.my_page_custom_tab, null);
                                tabBinding.tabCount.text=" "+it.myPageUserData.totalCherish.toString()

                                binding.myPageBottomTab.getTabAt(0)!!.customView=tabBinding.root


                                Log.d("list", it.myPageUserData.result.toString())


                            }
                    }
                })
    }

}