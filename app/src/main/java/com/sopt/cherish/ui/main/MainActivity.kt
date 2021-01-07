package com.sopt.cherish.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.main.home.HomeFragment
import com.sopt.cherish.ui.main.manageplant.ManagePlantFragment
import com.sopt.cherish.ui.main.setting.SettingFragment


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        initializeViewModel()

        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, HomeFragment()).commit()

        // bottomNavi 보단 bottomNavigation? or something 로직 함수로 뺴놓으면 좋을거 같음
        navigate(binding)
    }

    private fun initializeViewModel() {
        viewModel =
            ViewModelProvider(this@MainActivity, Injection.provideMainViewModelFactory()).get(
                MainViewModel::class.java
            )
    }

    private fun navigate(binding: ActivityMainBinding) {
        binding.mainBottomNavi.setOnNavigationItemSelectedListener {
            val transAction = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.main_home -> {
                    transAction.replace(R.id.main_fragment_container, HomeFragment()).commit()
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

}

