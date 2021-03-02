package com.sopt.cherish.ui.enrollment

import android.annotation.SuppressLint
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
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentResultPlantBinding
import com.sopt.cherish.ui.main.MainActivity


class ResultPlantFragment : Fragment() {

    private lateinit var binding: FragmentResultPlantBinding
    private lateinit var progressDialog: AppCompatDialog

    private lateinit var enrollToolbar: Toolbar

    private val viewModel: EnrollmentViewModel by activityViewModels()

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_result_plant, container, false)
        binding = FragmentResultPlantBinding.bind(view)
        binding.plantExplanation.text = arguments?.getString("plant_explanation")
        Log.d("plantExplanation", arguments?.getString("plant_explanation").toString())


        if (arguments?.getString("plant_modify")?.contains("\n") == true) {
            binding.textViewModify.text = arguments?.getString("plant_modify")?.split("\n")?.get(0)
            binding.textViewModifyUnder.text =
                arguments?.getString("plant_modify")?.split("\n")?.get(
                    1
                )?.toString()

        }



        val urlstring = arguments?.getString("plant_url")
        Log.d("url", urlstring.toString())
        Glide.with(this).load(urlstring.toString()).into(binding.imageViewUrl)

        val plant_id_btn=arguments?.getInt("plant_id")

        Log.d("plant_id_btn",plant_id_btn.toString())


        when(plant_id_btn){
            1 -> {binding.startbtn.setBackgroundColor(R.color.plantid1)
                binding.textViewFlower.setTextColor(R.color.plantid1)
                binding.viewFlower.setBackgroundColor(R.color.plantid1)
                binding.tipBox.setBackgroundResource(R.drawable.plant_tip_box)
                binding.textViewflowerMean.setTextColor(R.color.plantid1)
            }
            2 -> {binding.startbtn.setBackgroundColor(R.color.plantid2)
                binding.textViewFlower.setTextColor(R.color.plantid2)
                binding.viewFlower.setBackgroundColor(R.color.plantid2)
              //  binding.tipBox.setBackgroundResource(R.drawable.plant_tip_box2)
                binding.textViewflowerMean.setTextColor(R.color.plantid2)

            }
            3 -> {binding.startbtn.setBackgroundColor(R.color.plantid3)
                binding.textViewFlower.setTextColor(R.color.plantid3)
                binding.viewFlower.setBackgroundColor(R.color.plantid3)
               // binding.tipBox.setBackgroundResource(R.drawable.plant_tip_box3)
                binding.textViewflowerMean.setTextColor(R.color.plantid3)

            }
            4 -> {binding.startbtn.setBackgroundColor(R.color.plantid4)
                binding.textViewFlower.setTextColor(R.color.plantid4)
                binding.viewFlower.setBackgroundColor(R.color.plantid4)
             //   binding.tipBox.setBackgroundResource(R.drawable.plant_tip_box4)
                binding.textViewflowerMean.setTextColor(R.color.plantid4)
            }
            5 -> {binding.startbtn.setBackgroundColor(R.color.plantid5)
                binding.textViewFlower.setTextColor(R.color.plantid5)
                binding.viewFlower.setBackgroundColor(R.color.plantid5)
                binding.tipBox.setBackgroundResource(R.drawable.plant_tip_box5)
                binding.textViewflowerMean.setTextColor(R.color.plantid5)
            }
        }

        binding.textViewflowerMean.text = arguments?.getString("plant_mean")



        binding.startbtn.setOnClickListener {
            // LoadingDialog를 보여주면 됨
            val intent = Intent(requireContext(), MainActivity::class.java)

            if (activity?.intent?.getIntExtra("codeFirstStart", -1) == 1) {
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("userId", viewModel.userId)
                intent.putExtra("userNickname", viewModel.userNickname)
                startActivity(intent)
            } else {
                activity?.finish()
                //  startActivity(intent)

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