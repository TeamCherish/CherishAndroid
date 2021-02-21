package com.sopt.cherish.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.ui.adapter.Phonemypage
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.home.HomeFragment
import com.sopt.cherish.ui.main.manageplant.*
import com.sopt.cherish.ui.main.setting.SettingFragment
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.SimpleLogger


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { Injection.provideMainViewModelFactory() }
    var search:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeViewModelData()
        showInitialFragment()
        getFirebaseDeviceToken()
        setBottomNavigationListener(binding)
    }


    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers()
    }

    private fun getFirebaseDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                SimpleLogger.logI("Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }
            val token = task.result
            SimpleLogger.logI(token.toString())
        })
    }

    private fun initializeViewModelData() {
        viewModel.cherishuserId.value = intent.getIntExtra("userId", 0)
        viewModel.userNickName.value = intent.getStringExtra("userNickname")
        viewModel.fetchUsers()
        SimpleLogger.logI(viewModel.cherishUsers.value.toString())
    }

    private fun showInitialFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, HomeFragment()).commit()
    }

    private fun setBottomNavigationListener(binding: ActivityMainBinding) {
        binding.mainBottomNavi.setOnNavigationItemSelectedListener {
            val transAction = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.main_home -> {
                    transAction.replace(R.id.main_fragment_container, HomeFragment().apply {
                        arguments = Bundle().apply {
                            putInt("userid", intent.getIntExtra("userId", 0))
                        }
                    }).commit()
                    true
                }
                R.id.main_manage_plant -> {
                    transAction.replace(R.id.main_fragment_container, ManagePlantFragment().apply {

                        arguments = Bundle().apply {

                            putString( "phonecount",getPhoneNumbers().toString())

                        }
                    })
                        .commit()
                    true

                }

                R.id.main_setting -> {
                    transAction.replace(R.id.main_fragment_container, SettingFragment()).commit()
                    true
                }
                else -> {
                    throw AssertionError()
                }
            }
        }
    }

    @SuppressLint("Recycle")
    fun getPhoneNumbers(): Int{
        val list = mutableListOf<Phonemypage>()

        val phonUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 2.1 전화번호에서 가져올 컬럼 정의
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        // 2.2 조건 정의
        var where: String? = null
        var whereValues: Array<String>? = null

        applicationContext?.run {
            val cursor = contentResolver.query(phonUri, projections, where, whereValues, "")

            while (cursor?.moveToNext() == true) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)

                val phone = Phonemypage(id, name, number)

                list.add(phone)
            }
        }
        // 결과목록 반환
        return list.size
    }

    fun replaceFragment(index: Int, data: List<MyPageCherishData>?, isSearched:Boolean) {
        search=isSearched
        val transAction = supportFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                if(isSearched) //검색창 있는 뷰
                    transAction.replace(R.id.my_page_bottom_container, PlantSearchFragment(data)).commit()
                else //검색창 없는 뷰
                    transAction.replace(R.id.my_page_bottom_container, PlantFragment(data)).commit()
            }
            1 -> {
                if (PermissionUtil.isCheckedReadContactsPermission(this)) {
                    if(isSearched)
                        transAction.replace(
                            R.id.my_page_bottom_container, MyPagePhoneBookSearchFragment()
                        ).commit()
                    else
                        transAction.replace(
                            R.id.my_page_bottom_container, MyPagePhoneBookFragment()
                        ).commit()
                } else {
                    PermissionUtil.openPermissionSettings(this)
                }
                //true
            }
        }
    }

    fun getIsSearched():Boolean{
        return search
    }

    fun setIsSearched(isSearched:Boolean){
        search=isSearched
    }
}
