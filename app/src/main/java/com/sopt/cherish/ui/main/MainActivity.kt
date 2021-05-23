package com.sopt.cherish.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Process
import android.provider.ContactsContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sopt.cherish.MainApplication
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
import com.sopt.cherish.util.createNeedToWateringUser
import com.sopt.cherish.util.createRecallReview
import com.sopt.cherish.util.extension.shortToast


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { Injection.provideMainViewModelFactory() }
    var search: Boolean = false
    private var backPressedTime: Long = 0
    var beforeClick = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        Injection.provideNotificationManager(this).let {
            it.createNeedToWateringUser(this)
            it.createRecallReview(this)
        }
        initializeToken()
        initializeViewModelData()
        requestCherishPermissions()
        showInitialFragment()
        getFirebaseDeviceToken()
        observeFirebaseDeviceToken()
        observeErrorHandleLiveData()
        setBottomNavigationListener(binding)
    }

    private fun observeErrorHandleLiveData() {
        viewModel.errorHandleLivedata.observe(this) {
            shortToast(this, "네트워크 상태를 확인해주세요")
        }
    }

    // 혹시나 마켓에 올릴 때 이거 없어서 문제생기면 그때 로직 변경
/*    override fun onBackPressed() {
        val TIME_INTERVAL: Long = 2000
        val currentTime = System.currentTimeMillis()
        val intervalTime = currentTime - backPressedTime
        if (intervalTime in 0..TIME_INTERVAL)
            finish()
        else {
            backPressedTime = currentTime
            shortToast(this, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다")
        }
    }*/

    private fun initializeToken() {
        /*
        intent?.getStringExtra("loginToken")?.let { token ->
            MainApplication.sharedPreferenceController.setToken(
                token
            )
         */
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
            MainApplication.sharedPreferenceController.run {
                if (!this.isSingleInvoke()) {
                    this.singleInvoked()
                    viewModel.sendFcmToken(NotificationReq(viewModel.cherishUserId.value!!, it))
                }
            }
        }
    }

    private fun initializeViewModelData() {
        viewModel.cherishUserId.value = intent?.getIntExtra("userId", -1)
        viewModel.userNickName.value = intent?.getStringExtra("userNickname")
        viewModel.fetchUsers()
    }

    private fun showInitialFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, HomeFragment()).commit()

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
                        shortToast(this, "권한이 설정되어 있지 않아 앱을 사용할 수 없습니다")
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
                                    viewModel.cherishUserId.value?.let { userId ->
                                        putInt(
                                            "userId",
                                            userId
                                        )
                                    }
                                    viewModel.userNickName.value?.let { userNickName ->
                                        putString(
                                            "userNickName",
                                            userNickName
                                        )
                                    }
                                }
                            }).commit()
                    } else {
                        shortToast(this, "전화번호부 권한을 주지 않아 갈 수 없습니다")
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

    fun replaceFragment(index: Int, data: List<MyPageCherishData>?, isSearched: Boolean) {
        search = isSearched
        val transAction = supportFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                if (isSearched) //검색창 있는 뷰
                    transAction.replace(R.id.my_page_bottom_container, PlantSearchFragment(data))
                        .commit()
                else //검색창 없는 뷰
                    transAction.replace(R.id.my_page_bottom_container, PlantFragment(data)).commit()
            }
            1 -> {
                if (PermissionUtil.isCheckedReadContactsPermission(this)) {
                    if (isSearched)
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

    fun getIsSearched(): Boolean {
        return search
    }

    private var pressedTime: Long = 0

    // 리스너 생성
    interface OnBackPressedListener {
        fun onBack()
    }

    // 리스너 객체 생성
    private var mBackListener: OnBackPressedListener? = null

    // 리스너 설정 메소드
    fun setOnBackPressedListener(listener: OnBackPressedListener?) {
        mBackListener = listener
    }

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    override fun onBackPressed() {

        // 다른 Fragment 에서 리스너를 설정했을 때 처리됩니다.
        if (mBackListener != null) {
            mBackListener!!.onBack()
            // 리스너가 설정되지 않은 상태(예를들어 메인Fragment)라면
            // 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료됩니다.
        } else {
            if (pressedTime == 0L) {

                pressedTime = System.currentTimeMillis()
            } else {
                val seconds = (System.currentTimeMillis() - pressedTime).toInt()
                if (seconds > 2000) {

                    pressedTime = 0
                } else {
                    super.onBackPressed()
                    finish()
                    Process.killProcess(Process.myPid())
                }
            }
        }
    }

}