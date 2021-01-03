package com.sopt.cherish.ui.main.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.ui.datail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.ui.enrollment.PhoneBookActivity


class secondActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enrollbtn.setOnClickListener {
            val intent= Intent(this, PhoneBookActivity::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            val intent= Intent(this,DetailPlantActivity::class.java)
            startActivity(intent)
        }

}