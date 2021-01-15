package com.sopt.cherish.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.enrollment.MyPagePhoneBookFragment
import com.sopt.cherish.ui.main.home.HomeFragment
import com.sopt.cherish.ui.main.manageplant.ManagePlantFragment
import com.sopt.cherish.ui.main.manageplant.PlantFragment
import com.sopt.cherish.ui.main.setting.SettingFragment
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.SimpleLogger


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onResume() {
        super.onResume()
        initializeViewModel()
        initializeViewModelData()
        showInitialFragment()
        setBottomNavigationListener(binding)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        initializeViewModel()
        initializeViewModelData()
        showInitialFragment()
        setBottomNavigationListener(binding)
    }

    private fun initializeViewModelData() {
        viewModel.userId.value = intent.getIntExtra("userId", 0)
        viewModel.userNickName.value = intent.getStringExtra("userNickname")
        SimpleLogger.logI(viewModel.userNickName.value.toString())
    }

    private fun initializeViewModel() {
        viewModel =
            ViewModelProvider(this@MainActivity, Injection.provideMainViewModelFactory()).get(
                MainViewModel::class.java
            )
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
                            Log.d("mainactivity", intent.getIntExtra("userId", 0).toString())
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
                // todo : 일단 퍼미션 처리는 했는데 , 이것보다 좀 더 좋게 해줄 수 있을거 같아 예진아
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

}
