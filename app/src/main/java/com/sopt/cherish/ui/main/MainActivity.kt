package com.sopt.cherish.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R
import com.sopt.cherish.ui.dialog.CustomDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * CustomDialogFragment 쓰는 법 잠시 강의 겸 버전 맞추기
         */
        CustomDialogFragment(R.layout.sample_lottie2).show(supportFragmentManager, "MainActivity")
    }
}