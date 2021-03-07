package com.sopt.cherish.ui.main

import android.annotation.SuppressLint
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
import com.sopt.cherish.remote.api.NotificationReq
import com.sopt.cherish.ui.adapter.Phonemypage
import com.sopt.cherish.ui.main.home.HomeFragment
import com.sopt.cherish.ui.main.manageplant.*
import com.sopt.cherish.ui.main.setting.SettingFragment
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.shortToast


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { Injection.provideMainViewModelFactory() }
    var search: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeViewModelData()
        requestCherishPermissions()
        showInitialFragment()
        getFirebaseDeviceToken()
        observeFirebaseDeviceToken()
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
            viewModel.fcmToken.value = token
            SimpleLogger.logI(token.toString())
        })
    }

    private fun observeFirebaseDeviceToken() {
        viewModel.fcmToken.observe(this) {
            viewModel.sendFcmToken(NotificationReq(viewModel.cherishuserId.value!!, it))
        }
    }

    private fun initializeViewModelData() {
        viewModel.cherishuserId.value = intent.getIntExtra("userId", -1)
        viewModel.userNickName.value = intent.getStringExtra("userNickname")
        viewModel.fetchUsers()
    }

    private fun showInitialFragment() {
        if (PermissionUtil.isCheckedCallPermission(this) && PermissionUtil.isCheckedSendMessagePermission(
                this
            )
        ) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, HomeFragment()).commit()
        } else {
            shortToast(this, "권한이 설정되어 있지 않아 앱을 실행할 수 없어요 ㅠ")
            openSettings()
        }
    }

    private fun requestCherishPermissions() {
        PermissionUtil.requestCherishPermission(this, object : PermissionUtil.PermissionListener {
            override fun onPermissionGranted() {

            }

            override fun onPermissionShouldBeGranted(deniedPermissions: List<String>) {
                shortToast(this@MainActivity, "권한 허용이 안되어있습니다. $deniedPermissions")
                openSettings()
            }

            override fun onAnyPermissionPermanentlyDenied(
                deniedPermissions: List<String>,
                permanentDeniedPermissions: List<String>
            ) {
                shortToast(this@MainActivity, "권한 허용이 영구적으로 거부되었습니다. $permanentDeniedPermissions")
                openSettings()
            }
        })
    }

    private fun openSettings() {
        PermissionUtil.openPermissionSettings(this)
    }

    private fun setBottomNavigationListener(binding: ActivityMainBinding) {
        binding.mainBottomNavi.setOnNavigationItemSelectedListener {
            val transAction = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.main_home -> {
                    if (PermissionUtil.isCheckedSendMessagePermission(this) && PermissionUtil.isCheckedCallPermission(
                            this
                        )
                    ) {
                        transAction.replace(R.id.main_fragment_container, HomeFragment().apply {
                            arguments = Bundle().apply {
                                putInt("userid", intent.getIntExtra("userId", 0))
                            }
                        }).commit()
                    } else {
                        shortToast(this, "권한이 설정되어 있지 않아 앱을 사용할 수 없어요 ㅠ")
                        openSettings()
                    }
                    true
                }
                R.id.main_manage_plant -> {
                    if (PermissionUtil.isCheckedReadContactsPermission(this)) {
                        transAction.replace(
                            R.id.main_fragment_container,
                            ManagePlantFragment().apply {
                                arguments = Bundle().apply {
                                    putString("phonecount", getPhoneNumbers().toString())
                                }
                            }).commit()
                    } else {
                        shortToast(this, "전화번호부 권한을 주지 않아 갈 수 없어요 ㅠ")
                        openSettings()
                    }
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
    fun getPhoneNumbers(): Int {
        val list = mutableListOf<Phonemypage>()

        val phonUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        // 2.1 전화번호에서 가져올 컬럼 정의
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        // 2.2 조건 정의
        val where: String? = null
        val whereValues: Array<String>? = null

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

    fun replaceFragment(index: Int, data: ArrayList<MyPageCherishData>?) {
        val transAction = supportFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                transAction.replace(R.id.my_page_bottom_container, PlantFragment(data)).commit()
            }
            1 -> {
                if (PermissionUtil.isCheckedReadContactsPermission(this)) {
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

}
