package com.sopt.cherish.ui.main.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityAboutcherishBinding
import com.sopt.cherish.databinding.ActivityDetailPlantBinding
import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.main.MainViewModel

class AboutCherishActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAboutcherishBinding = ActivityAboutcherishBinding.inflate(layoutInflater)


        binding.imageButtonAboutCherish.setOnClickListener {
            finish()
        }

        setContentView(binding.root)

    }
}