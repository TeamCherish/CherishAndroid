package com.sopt.cherish.ui.enrollment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentEnrollPlantBinding
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.WeekPickerDialogFragment


class EnrollPlantFragment : Fragment(){



    private lateinit var binding: FragmentEnrollPlantBinding

    private lateinit var progressDialog: AppCompatDialog
    private lateinit var enrollToolbar: Toolbar


    lateinit var weektime: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_enroll_plant, container, false)

        binding = FragmentEnrollPlantBinding.bind(view)

        //enrollToolbar.title="식물 상세 입력"

        binding.phoneName.text = arguments?.getString("phonename")
        binding.phoneNumber.text = arguments?.getString("phonenumber")




        binding.detailOkBtn.setOnClickListener {
            progressON()


            progressOFF()


            //val intent = Intent(context, ResultPlantActivity::class.java)

           // intent.putExtra("plantkey", binding.waterAlarmWeek.text.toString())
           // startActivity(intent)

            //setFragment(ResultPlantFragment())

            setFragment(ResultPlantFragment())

        }
        //timepicker 나오는 부분
        binding.editclock.setOnClickListener {
            val needWaterDialog =ClockPickerDialogFragment(R.layout.clockpicker_layout) .show(
                parentFragmentManager,
                "MainActivity"
            )

        }

        binding.editweek.setOnClickListener {

            val needweek = WeekPickerDialogFragment(R.layout.weekpicker_layout).show(
                parentFragmentManager,
                "MainActivity"
            )


        }


        return view
    }


    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as EnrollmentPhoneActivity).setActionBarTitle("식물 상세 입력")

        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)




    }



    fun setFragment(fragment: Fragment){
        val transaction=parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_enroll, fragment.apply {
            arguments = Bundle().apply {
                putString("plantkey", binding.waterAlarmWeek.text.toString())

            }
        })
        transaction.addToBackStack(null)

        transaction.commit()
    }


    fun progressON() {
        progressDialog = AppCompatDialog(context)
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