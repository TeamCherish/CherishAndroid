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
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentEnrollPlantBinding
import com.sopt.cherish.remote.api.RetrofitBuilder
import com.sopt.cherish.remote.model.RequestEnrollData
import com.sopt.cherish.remote.model.ResponseEnrollData
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.WeekPickerDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EnrollPlantFragment : Fragment() {


    private lateinit var binding: FragmentEnrollPlantBinding

    private lateinit var progressDialog: AppCompatDialog
    private lateinit var enrollToolbar: Toolbar


    lateinit var weektime: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enroll_plant, container, false)

        binding = FragmentEnrollPlantBinding.bind(view)

        //enrollToolbar.title="식물 상세 입력"

        binding.phoneName.text = arguments?.getString("phonename")
        binding.phoneNumber.text = arguments?.getString("phonenumber")




        binding.detailOkBtn.setOnClickListener {
          //  progressON()

            val body = RequestEnrollData(name = "안나영",
                nickname ="nayoung",
                birth="19930512",
                phone= "010-1111-1111",
                cycle_date =7 ,
                notice_time ="15:00" ,
            UserId= 2)
            val call: Call<ResponseEnrollData> = RetrofitBuilder.retrofitService.postUsers(body)
            call.enqueue(object : Callback<ResponseEnrollData> {
                override fun onFailure(call: Call<ResponseEnrollData>, t: Throwable) {
                    Log.d("response", "실패")
                }
                override fun onResponse(
                    call: Call<ResponseEnrollData>,
                    response: Response<ResponseEnrollData>
                ) {
                    Log.d("response", response.body().toString())
                    response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {


                            Log.d("apple", it.data.plant.name)

                            val transaction = parentFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_enroll, ResultPlantFragment().apply {
                                arguments = Bundle().apply {
                                    putString("id", it.data.plant.id.toString())
                                    putString("name", it.data.plant.name.toString())
                                    putString("explanation", it.data.plant.explanation.toString())
                                    putString("modifier", it.data.plant.modifier.toString())
                                    putString("flower_meaning", it.data.plant.flower_meaning.toString())

                                }
                            })
                            transaction.addToBackStack(null)

                            transaction.commit()
                        //    it.data.plant.PlantStatusId
                            progressOFF()
                           // setFragment(ResultPlantFragment())
                        } ?: Toast.makeText(view!!.context, "error", Toast.LENGTH_SHORT).show()
                }
            })




            //val intent = Intent(context, ResultPlantActivity::class.java)

            // intent.putExtra("plantkey", binding.waterAlarmWeek.text.toString())
            // startActivity(intent)

            //setFragment(ResultPlantFragment())

            setFragment(ResultPlantFragment())

        }
        //timepicker 나오는 부분
        binding.editclock.setOnClickListener {
            val needWaterDialog = ClockPickerDialogFragment(R.layout.clockpicker_layout).show(
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


    fun setFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
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