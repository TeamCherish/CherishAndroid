package com.sopt.cherish.ui.main.manageplant


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentManagePlantBinding
import com.sopt.cherish.databinding.MyPageCustomTabBinding
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.remote.api.MyPageUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.adapter.MypagePhoneBookSearchAdapter
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.enrollment.PhoneBookFragment
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.main.setting.UserModifyFragment
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
    var isSearched: Boolean = false
    private val requestData = RetrofitBuilder
    private lateinit var tabBindingFirst: MyPageCustomTabBinding
    private lateinit var tabBindingSecond: MyPageCustomTabBinding
    lateinit var data: List<MyPageCherishData>
    lateinit var binding: FragmentManagePlantBinding
    var mypageusername:String=""
    var mypageuseremail:String=""
    lateinit var madapter: MypagePhoneBookSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_manage_plant, container, false)

        tabBindingFirst = MyPageCustomTabBinding.inflate(inflater, container, false)
        tabBindingSecond = MyPageCustomTabBinding.inflate(inflater, container, false)


        // 예진이 userId , viewModel.userId.value 라고하면 userId 찾을 수 있어요
        SimpleLogger.logI(viewModel.cherishuserId.value.toString())
        setTabLayout()
        initializeServerRequest(binding)

        binding.myPageAddPlantBtn.setOnClickListener {
            navigatePhoneBook()
        }

        binding.goToSetting.setOnClickListener {
            navigateUserModifyFragment()
        }
        initializeSearchBtn()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        setTabLayout()
        initializeServerRequest(binding)
        initializeSearchBtn()
        initializeBottomSheetBehavior(binding)
    }

    private fun initializeSearchBtn() {
        isSearched = (activity as MainActivity).getIsSearched()

        if (isSearched)
            binding.searchBox.visibility = View.INVISIBLE
        else
            binding.searchBox.visibility = View.VISIBLE
    }

    private fun initializeBottomSheetBehavior(binding: FragmentManagePlantBinding) {
        val standardBottomSheetBehavior =
            BottomSheetBehavior.from(binding.homeStandardBottomSheetMypage)
        // bottom sheet state 지정
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 300.dp
        standardBottomSheetBehavior.expandedOffset = 68.dp
        standardBottomSheetBehavior.isHideable = false

        //검색 버튼 눌렀을 때
        binding.searchBox.setOnClickListener {
            binding.searchBox.visibility = View.INVISIBLE
            isSearched = true
            binding.cancelBtn.visibility = View.VISIBLE
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            if (tabIndex == 1) {
                binding.myPageAddPlantBtn.visibility = View.INVISIBLE

            }
            (activity as MainActivity).replaceFragment(tabIndex, data, isSearched)
            /*    val intent=Intent(context,ManagePlantActivity::class.java)
                intent.putExtra("searchuserid",viewModel.cherishuserId.value)
                startActivity(intent)*/
        }
        binding.goToSetting.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, UserModifyFragment().apply {
                arguments = Bundle().apply {
                    putString("settingusernickname", mypageusername)
                    putString("settinguseremail", mypageuseremail)

                }
            })
            transaction.addToBackStack(null)
            transaction.commit()
        }


        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState==BottomSheetBehavior.STATE_DRAGGING){
                    binding.myPageBg.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_my_page_bg
                        )
                    )
                }

                if (tabIndex == 0) { //식물 탭 클릭 시

                    if (newState == BottomSheetBehavior.STATE_EXPANDED) { //바텀시트 확장됐을 경우
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )
                        isSearched = (activity as MainActivity).getIsSearched()
                        binding.myPageAddPlantBtn.visibility = View.VISIBLE //식물 추가 visible
                        isCollapsed = false
                    }

                    if(newState==BottomSheetBehavior.STATE_DRAGGING){
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )
                    }

                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) { //바텀시트 축소됐을 경우
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )
                        binding.searchBox.visibility = View.VISIBLE
                        binding.cancelBtn.visibility = View.INVISIBLE
                        binding.myPageAddPlantBtn.visibility = View.INVISIBLE //식물 추가 invisible
                        isCollapsed = true
                        isSearched = false

                    }

                }
                if (tabIndex == 1) { //연락처 탭 클릭 시
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) { //바텀시트 확장됐을 경우
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )

                        isSearched = (activity as MainActivity).getIsSearched()
                        if (isSearched) {
                            binding.myPageAddPlantBtn.visibility = View.INVISIBLE
                        } else {
                            binding.myPageAddPlantBtn.visibility = View.VISIBLE
                        }
                        isCollapsed = false
                    }


                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )
                        binding.searchBox.visibility = View.VISIBLE

                        binding.cancelBtn.visibility = View.INVISIBLE

                        binding.myPageAddPlantBtn.visibility = View.INVISIBLE
                        isCollapsed = true
                        isSearched = false
                    }

                }
                (activity as MainActivity).replaceFragment(tabIndex, data, isSearched)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }


        })
    }

    private fun setTabLayout(){

        if(binding.myPageBottomTab.getTabAt(0)==null){
            binding.myPageBottomTab.addTab(
                binding.myPageBottomTab.newTab().setCustomView(
                    createTabView(
                        "식물 ",
                        "0"
                    )
                )
            )
        }

        if(binding.myPageBottomTab.getTabAt(1)==null){
            binding.myPageBottomTab.addTab(
                binding.myPageBottomTab.newTab().setCustomView(
                    createTabView(
                        "연락처 ",
                        "0"
                    )
                )
            )
        }
    }

    private fun initializeTabLayoutView(
        binding: FragmentManagePlantBinding,
        data: List<MyPageCherishData>
    ) {
        when(tabIndex){
            0->{
                tabBindingFirst.tabName.setTextAppearance(R.style.MyPageTabSelected)
                tabBindingFirst.tabCount.setTextAppearance(R.style.MyPageTabSelected)

                tabBindingSecond.tabName.setTextAppearance(R.style.MyPageTab)
                tabBindingSecond.tabCount.setTextAppearance(R.style.MyPageTab)

                tabBindingFirst.tabName.setTextColor(Color.parseColor("#454545"))
                tabBindingFirst.tabCount.setTextColor(Color.parseColor("#1AD287"))

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.my_page_bottom_container, PlantFragment(data)).commit()

        for (i in 0 until binding.myPageBottomTab.tabCount) {
            val tab = (binding.myPageBottomTab.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(24, 0, 0, 0)
            tab.requestLayout()
        }

        binding.myPageBottomTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabIndex = binding.myPageBottomTab.selectedTabPosition

                if (tabIndex == 0) {
                    tabBindingFirst.tabName.setTextAppearance(R.style.MyPageTabSelected)
                    tabBindingFirst.tabCount.setTextAppearance(R.style.MyPageTabSelected)

                    tabBindingSecond.tabName.setTextAppearance(R.style.MyPageTab)
                    tabBindingSecond.tabCount.setTextAppearance(R.style.MyPageTab)

                    tabBindingFirst.tabName.setTextColor(Color.parseColor("#454545"))
                    tabBindingFirst.tabCount.setTextColor(Color.parseColor("#1AD287"))

                    if (isCollapsed) {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )
                        binding.cancelBtn.visibility = View.INVISIBLE
                        binding.myPageAddPlantBtn.visibility = View.INVISIBLE
                        isSearched = false

                    } else {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )
                        isSearched = (activity as MainActivity).getIsSearched()

                        binding.myPageAddPlantBtn.visibility = View.VISIBLE
                    }
                    (activity as MainActivity).replaceFragment(tabIndex, data, isSearched)
                }
                if (tabIndex == 1) {
                    tabBindingFirst.tabName.setTextAppearance(R.style.MyPageTab)
                    tabBindingFirst.tabCount.setTextAppearance(R.style.MyPageTab)

                    tabBindingSecond.tabName.setTextAppearance(R.style.MyPageTabSelected)
                    tabBindingSecond.tabCount.setTextAppearance(R.style.MyPageTabSelected)

                    tabBindingSecond.tabName.setTextColor(Color.parseColor("#454545"))
                    tabBindingSecond.tabCount.setTextColor(Color.parseColor("#1AD287"))

                    if (isCollapsed) {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_my_page_bg
                            )
                        )

                        binding.cancelBtn.visibility = View.INVISIBLE

                        isSearched = false

                    } else {
                        binding.myPageBg.setBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )

                        isSearched = (activity as MainActivity).getIsSearched()
                        if (isSearched) {
                            binding.myPageAddPlantBtn.visibility = View.INVISIBLE
                        } else {
                            binding.myPageAddPlantBtn.visibility = View.VISIBLE
                        }
                    }
                    (activity as MainActivity).replaceFragment(tabIndex, data, isSearched)
                }
            }
        })

    }

    private fun createTabView(name: String, count: String?): LinearLayout {
        return when (name) {
            "식물 " -> {
                tabBindingFirst.tabName.text = name
                tabBindingFirst.tabCount.text = count

                tabBindingFirst.root
            }
            else -> {
                tabBindingSecond.tabName.text = name
                tabBindingSecond.tabCount.text = count

                tabBindingSecond.root
            }
        }

    }

    private fun navigatePhoneBook() {
        val intent = Intent(context, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.cherishuserId.value!!)
        startActivity(intent)
    }


    private fun initializeServerRequest(binding: FragmentManagePlantBinding) {
        requestData.myPageAPI.fetchUserPage(viewModel.cherishuserId.value!!)
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
                                binding.myPageUserName.text = it.myPageUserData.user_nickname
                                mypageusername=it.myPageUserData.user_nickname
                                mypageuseremail=it.myPageUserData.email
                                binding.myPageBottomTab.getTabAt(0)!!.setCustomView(
                                    createTabView(
                                        "식물 ",
                                        it.myPageUserData.totalCherish.toString()
                                    )
                                )
                                binding.myPageBottomTab.getTabAt(1)!!.setCustomView(
                                    createTabView(
                                        "연락처 ",
                                        arguments?.getString("phonecount")
                                    )
                                )

                                initializeTabLayoutView(
                                    binding,
                                    it.myPageUserData.result
                                )
                                data = it.myPageUserData.result
                                Log.d("list", it.myPageUserData.result.toString())
                            }
                    }
                })
    }


}