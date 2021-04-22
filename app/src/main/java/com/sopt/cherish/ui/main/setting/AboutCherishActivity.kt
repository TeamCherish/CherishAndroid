package com.sopt.cherish.ui.main.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivityAboutcherishBinding

class AboutCherishActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAboutcherishBinding =
            ActivityAboutcherishBinding.inflate(layoutInflater)


        binding.imageButtonAboutCherish.setOnClickListener {
            finish()
        }

        setContentView(binding.root)

    }
}