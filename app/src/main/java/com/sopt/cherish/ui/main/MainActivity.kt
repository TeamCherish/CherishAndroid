package com.sopt.cherish.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.R
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.dialog.WateringDialogFragment

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()

        /**
         * CustomDialogFragment 쓰는 법 잠시 강의 겸 버전 맞추기
         */
        WateringDialogFragment().show(supportFragmentManager, "MainActivity")
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this@MainActivity, Injection.provideMainViewModelFactory()).get(
                MainViewModel::class.java
        )
    }
}