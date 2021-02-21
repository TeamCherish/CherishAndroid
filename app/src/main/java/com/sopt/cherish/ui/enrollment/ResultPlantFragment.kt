package com.sopt.cherish.ui.enrollment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentResultPlantBinding
import com.sopt.cherish.ui.main.MainActivity


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
        binding = FragmentResultPlantBinding.bind(view)
        binding.plantExplanation.text = arguments?.getString("plant_explanation")
        Log.d("plantExplanation", arguments?.getString("plant_explanation").toString())


        if(arguments?.getString("plant_modify")?.contains("\n") == true) {
            binding.textViewModify.text = arguments?.getString("plant_modify")?.split("\n")?.get(0)
            binding.textViewModifyUnder.text =
                arguments?.getString("plant_modify")?.split("\n")?.get(
                    1
                )?.toString()

        }

        binding.textView1Mean.text = arguments?.getString("plant_mean")

        val urlstring = arguments?.getString("plant_url")
        Log.d("url", urlstring.toString())
        Glide.with(this).load(urlstring.toString()).into(binding.imageViewUrl)

        binding.startbtn.setOnClickListener {
            // LoadingDialog를 보여주면 됨
            if (activity?.intent?.getIntExtra("codeFirstStart", -1) == 1) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                // userId , userNickname이 없어서 안되는거네 시발 찾았다.
                // todo : userId랑 userNickname 받으면된다.
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                activity?.finish()
            }
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
}