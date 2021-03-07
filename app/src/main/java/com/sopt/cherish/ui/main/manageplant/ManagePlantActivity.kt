package com.sopt.cherish.ui.main.manageplant


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityManageplantBinding
import com.sopt.cherish.databinding.MyPageCustomTabBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.remote.api.MyPageUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.enrollment.EnrollmentViewModel
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.util.PermissionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagePlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageplantBinding
    private val viewModel: EnrollmentViewModel by viewModels { Injection.provideEnrollmentViewModelFactory() }
    private var tabIndex:Int=0
    private var tabIndexChanged:Int=0
    var data: ArrayList<MyPageCherishData>?=null
    var plantCount:String?=""
    var phoneCount:String?=""
    private var userId:Int=0
    private val requestData = RetrofitBuilder
    private lateinit var tabBindingFirst: MyPageCustomTabBinding
    private lateinit var tabBindingSecond: MyPageCustomTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageplantBinding.inflate(layoutInflater)

        tabBindingFirst = MyPageCustomTabBinding.inflate(layoutInflater)
        tabBindingSecond = MyPageCustomTabBinding.inflate(layoutInflater)

        val intent= getIntent()
        tabIndex=intent.getIntExtra("tabIndex",0)
        userId=intent.getIntExtra("userId",0)
        //plantCount=intent.getStringExtra("plantCount")
        phoneCount=intent.getStringExtra("phoneCount")
        //data=intent.getSerializableExtra("plantData") as ArrayList<MyPageCherishData>?

        Log.d("getTabIndex",tabIndex.toString())
        Log.d("userId",userId.toString())
        //Log.d("getPlantData",data.toString())
        //Log.d("getPlantCount",plantCount.toString())
        Log.d("getPhoneCount",phoneCount.toString())
        setTabLayout()
        initializeServerRequest(binding)

        binding.cancelBtn.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setTabLayout()
        initializeServerRequest(binding)
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

    fun replaceFragment(index: Int, data: ArrayList<MyPageCherishData>?) {
        val transAction = supportFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                transAction.replace(R.id.frame_container, PlantSearchFragment(data)).commit()
            }
            1 -> {
                if (PermissionUtil.isCheckedReadContactsPermission(this)) {
                    transAction.replace(
                        R.id.frame_container, MyPagePhoneBookSearchFragment()
                    ).commit()
                } else {
                    PermissionUtil.openPermissionSettings(this)
                }
                //true
            }
        }
    }

    private fun initializeTabLayoutView(
        binding: ActivityManageplantBinding,
        data: ArrayList<MyPageCherishData>
    ) {

        when(tabIndex){
            0->{
                tabBindingFirst.tabName.setTextAppearance(R.style.MyPageTabSelected)
                tabBindingFirst.tabCount.setTextAppearance(R.style.MyPageTabSelected)

                tabBindingSecond.tabName.setTextAppearance(R.style.MyPageTab)
                tabBindingSecond.tabCount.setTextAppearance(R.style.MyPageTab)

                tabBindingFirst.tabName.setTextColor(Color.parseColor("#454545"))
                tabBindingFirst.tabCount.setTextColor(Color.parseColor("#1AD287"))

                this.supportFragmentManager.beginTransaction()
                    .add(R.id.frame_container, PlantSearchFragment(data)).commit()
            }
            1->{
                tabBindingFirst.tabName.setTextAppearance(R.style.MyPageTab)
                tabBindingFirst.tabCount.setTextAppearance(R.style.MyPageTab)

                tabBindingSecond.tabName.setTextAppearance(R.style.MyPageTabSelected)
                tabBindingSecond.tabCount.setTextAppearance(R.style.MyPageTabSelected)

                tabBindingSecond.tabName.setTextColor(Color.parseColor("#454545"))
                tabBindingSecond.tabCount.setTextColor(Color.parseColor("#1AD287"))

                this.supportFragmentManager.beginTransaction()
                    .add(R.id.frame_container,MyPagePhoneBookSearchFragment()).commit()
            }
        }


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
                    binding.myPageAddPlantBtn.visibility = View.VISIBLE

                    tabBindingFirst.tabName.setTextAppearance(R.style.MyPageTabSelected)
                    tabBindingFirst.tabCount.setTextAppearance(R.style.MyPageTabSelected)

                    tabBindingSecond.tabName.setTextAppearance(R.style.MyPageTab)
                    tabBindingSecond.tabCount.setTextAppearance(R.style.MyPageTab)

                    tabBindingFirst.tabName.setTextColor(Color.parseColor("#454545"))
                    tabBindingFirst.tabCount.setTextColor(Color.parseColor("#1AD287"))


                    replaceFragment(tabIndex, data)
                }
                if (tabIndex == 1) {
                    binding.myPageAddPlantBtn.visibility = View.INVISIBLE

                    tabBindingFirst.tabName.setTextAppearance(R.style.MyPageTab)
                    tabBindingFirst.tabCount.setTextAppearance(R.style.MyPageTab)

                    tabBindingSecond.tabName.setTextAppearance(R.style.MyPageTabSelected)
                    tabBindingSecond.tabCount.setTextAppearance(R.style.MyPageTabSelected)

                    tabBindingSecond.tabName.setTextColor(Color.parseColor("#454545"))
                    tabBindingSecond.tabCount.setTextColor(Color.parseColor("#1AD287"))

                    replaceFragment(tabIndex, data)
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

    private fun initializeServerRequest(binding: ActivityManageplantBinding) {
        requestData.myPageAPI.fetchUserPage(userId)
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


                                plantCount=it.myPageUserData.totalCherish.toString()
                                //phoneCount=arguments?.getString("phonecount")

                                binding.myPageBottomTab.getTabAt(0)!!.setCustomView(
                                    createTabView(
                                        "식물 ",
                                        it.myPageUserData.totalCherish.toString()
                                    )
                                )
                                binding.myPageBottomTab.getTabAt(1)!!.setCustomView(
                                    createTabView(
                                        "연락처 ",
                                        phoneCount
                                    )
                                )
                                initializeTabLayoutView(
                                    binding,
                                    it.myPageUserData.result
                                )
                                data = it.myPageUserData.result
                                //Log.d("list", it.myPageUserData.result.toString())
                            }
                    }
                })
    }
}