package com.sopt.cherish.ui.enrollment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityResultplantBinding

//created by nayoung : 식물 배정된 창이 나오는 activity
class ResultPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultplantBinding
    private lateinit var progressDialog: AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultplantBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbarResult)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)

        if (intent.getStringExtra("plantkey") == "1") {
            binding.imageView2.setImageResource(R.drawable.cherry)
        } else if (intent.getStringExtra("plantkey") == "2") {
            binding.imageView2.setImageResource(R.drawable.cherry2)

        }

        binding.startbtn.setOnClickListener {
            progressON()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun progressON() {
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.progress_layout)
        progressDialog.show()
    }

    private fun progressOFF() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}


