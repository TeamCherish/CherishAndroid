package com.sopt.cherish.ui.enrollment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.sopt.cherish.R
import com.sopt.cherish.databinding.AcitivityEnrollplantBinding
import com.sopt.cherish.databinding.WeekpickerLayoutBinding
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.WeekPickerDialogFragment

class EnrollPlantActicity : AppCompatActivity() {

    private lateinit var binding: AcitivityEnrollplantBinding
    private lateinit var bindingpicker: WeekpickerLayoutBinding

    private lateinit var progressDialog: AppCompatDialog
    private val time: Long = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityEnrollplantBinding.inflate(layoutInflater)
        bindingpicker = WeekpickerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener {
            val intent = Intent(this, PhoneBookActivity::class.java)
            startActivity(intent)
        }
        binding.detailOkBtn.setOnClickListener {
            progressON()
            Handler().postDelayed({
                // This method will be executed once the timer is over
                // Start your app main activity

                //startActivity(Intent(this, MainActivity::class.java))
                progressOFF()
                // close this activity
                finish()
            }, time)


            val intent = Intent(this, ResultPlantActivity::class.java)

            intent.putExtra("plantkey", binding.editweek.text.toString())
            startActivity(intent)

        }

        binding.editclock.setOnClickListener {
            val needWaterDialog = ClockPickerDialogFragment(R.layout.clockpicker_layout).show(supportFragmentManager, "MainActivity")


        }
        binding.editweek.setOnClickListener {

            WeekPickerDialogFragment(R.layout.weekpicker_layout).show(supportFragmentManager, "MainActivity")


        }


    }


    fun progressON() {
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    fun progressOFF() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}