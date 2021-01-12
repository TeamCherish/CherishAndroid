package com.sopt.cherish.ui.enrollment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentResultPlantBinding

import com.sopt.cherish.remote.model.ResponseEnrollData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResultPlantFragment : Fragment() {

    private lateinit var binding: FragmentResultPlantBinding
    private lateinit var progressDialog: AppCompatDialog

    private lateinit var enrollToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_result_plant, container, false)
        //enrollToolbar.title="식물 결과"
        binding = FragmentResultPlantBinding.bind(view)

      /*  if (arguments?.getString("plantkey") == "1") {
            binding.imageView2.setImageResource(R.drawable.cherry)
        } else if (arguments?.getString("plantkey") == "2") {
            binding.imageView2.setImageResource(R.drawable.cherry2)

        }*/

        //val token = MyApplication.mySharedPreferences.getValue("token","")

        binding.textView11.text=arguments?.getString("id")
        binding.textView21.text=arguments?.getString("name")
        binding.plantExplanation.text=arguments?.getString("explanation")


        binding.startbtn.setOnClickListener {
            progressON()
        }




        return view
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as EnrollmentPhoneActivity).setActionBarTitle("식물 결과")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun progressON() {
        progressDialog = AppCompatDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.progress_layout)
        progressDialog.show()

    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

}