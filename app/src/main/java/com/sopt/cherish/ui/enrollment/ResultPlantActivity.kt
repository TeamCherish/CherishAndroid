package com.sopt.cherish.ui.enrollment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityResultplantBinding

class ResultPlantActivity :AppCompatActivity() {

    private lateinit var binding: ActivityResultplantBinding
    private lateinit var progressDialog: AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultplantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("asdf",intent.getStringExtra("plantkey").toString())

            if(intent.getStringExtra("plantkey")=="1"){
                binding.imageView2.setImageResource(R.drawable.cherry)
            }
            else if(intent.getStringExtra("plantkey")=="2"){
                binding.imageView2.setImageResource(R.drawable.cherry2)

            }


        binding.startbtn.setOnClickListener {
            progressON()
        }
        binding.imageButton3.setOnClickListener {
            val intent=Intent(this,EnrollPlantActicity::class.java)
            startActivity(intent)
        }


    }
    fun progressON(){
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.progress_layout)
        progressDialog.show()
        /*var img_loading_framge = progressDialog.findViewById<ImageView>(R.id.GIFimage)
        var frameAnimation = img_loading_framge?.getBackground() as AnimationDrawable
        img_loading_framge?.post(object : Runnable{
            override fun run() {
                frameAnimation.start()
            }

        })*/
    }
    fun progressOFF(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss()
        }
    }
}


