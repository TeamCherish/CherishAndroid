package com.sopt.cherish.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.enrollment.MyPagePhoneBookFragment
import com.sopt.cherish.ui.main.home.HomeFragment
import com.sopt.cherish.ui.main.manageplant.ManagePlantFragment
import com.sopt.cherish.ui.main.manageplant.PlantFragment
import com.sopt.cherish.ui.main.setting.SettingFragment
import com.sopt.cherish.ui.review.ReviewFragment
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.longToast


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { Injection.provideMainViewModelFactory() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        initializeViewModelData()
        showInitialFragment()
        getFirebaseDeviceToken()
        observeExceptions()
        setBottomNavigationListener(binding)
    }

    private fun observeExceptions() {
        viewModel.exceptionLiveData.observe(this) {
            longToast(this, it)
        }
    }

    override fun onResume() {
        super.onResume()
        showInitialFragment()
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
        viewModel.userId.value = intent.getIntExtra("userId", 0)
        viewModel.userNickName.value = intent.getStringExtra("userNickname")
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
                    transAction.replace(R.id.main_fragment_container, ManagePlantFragment())
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

    fun replaceFragment(index: Int) {
        val transAction = supportFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                transAction.replace(R.id.my_page_bottom_container, PlantFragment()).commit()
            }
            1 -> {
                if (PermissionUtil.isCheckedReadContactsPermission(this)) {
                    transAction.replace(
                        R.id.my_page_bottom_container, MyPagePhoneBookFragment()
                    )
                        .commit()
                } else {
                    PermissionUtil.openPermissionSettings(this)
                }
                //true
            }
        }
    }

    fun showReviewFragment() {
        val transAction = supportFragmentManager.beginTransaction()
        transAction.replace(R.id.home_parent_fragment_container, ReviewFragment()).commit()
    }

}
