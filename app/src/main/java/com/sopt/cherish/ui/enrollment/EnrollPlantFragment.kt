package com.sopt.cherish.ui.enrollment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentEnrollPlantBinding
import com.sopt.cherish.remote.model.RequestEnrollData
import com.sopt.cherish.remote.model.ResponseEnrollData

import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.WeekPickerDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EnrollPlantFragment : Fragment() {


    private lateinit var binding: FragmentEnrollPlantBinding

    private lateinit var progressDialog: AppCompatDialog
    private lateinit var enrollToolbar: Toolbar

    private val requestData = RetrofitBuilder
    lateinit var weektime: String
    var switchvalue: Boolean = false

    var plant_explanation: String = ""
    lateinit var plant_modify: String

    lateinit var plant_mean: String
    lateinit var plant_url: String

    var user_water = 0

    @SuppressLint("ResourceAsColor")
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

        binding.alarmSwitch.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                switchvalue = true
            }

        }



        binding.detailOkBtn.setOnClickListener {
            //  progressON()

            val username = arguments?.getString("phonename")
            val usernickname = binding.editNickname.text.toString()
            val userbirth = binding.editBirth.text.toString()

            val userphone = arguments?.getString("phonenumber")
            val userphonebook = userphone?.substring(0, 3) + "-" + userphone?.substring(
                3,
                7
            ) + "-" + userphone?.substring(7)
            val userwater = binding.waterAlarmWeek.text.toString()
            user_water = userwater.substring(6, 7).toInt()


            if (userwater.substring(8) == "month") {
                user_water = userwater.substring(6, 7).toInt() * 30
                Log.d("userwater", user_water.toString())
            } else if (userwater.substring(8) == "week") {
                user_water = (userwater.substring(6, 7).toInt()) * 7
                Log.d("userwater", user_water.toString())
            } else {
                user_water = userwater.substring(6, 7).toInt()
                Log.d("userwater", user_water.toString())
            }

            val usertime = binding.waterAlarmTime.text.toString()

            val userid = arguments?.getInt("useridend")?.toInt()
            Log.d("enrollplnatfrgment", userid.toString())
            val body = userid?.let { it1 ->
                RequestEnrollData(
                    name = username.toString(),
                    nickname = usernickname,
                    birth = userbirth,
                    phone = userphonebook,
                    cycle_date = user_water,
                    notice_time = usertime,
                    water_notice = switchvalue,
                    UserId = it1
                )
            }


            if (body != null) {
                requestData.enrollAPI.enrollCherish(body)
                    .enqueue(
                        object : Callback<ResponseEnrollData> {
                            override fun onFailure(call: Call<ResponseEnrollData>, t: Throwable) {
                                Log.d("통신 실패", t.toString())
                            }

                            override fun onResponse(
                                call: Call<ResponseEnrollData>,
                                response: Response<ResponseEnrollData>
                            ) {
                                Log.d("success", response.body().toString())
                                response.takeIf {
                                    it.isSuccessful
                                }?.body()
                                    ?.let { it ->

                                        Log.d("data success!", it.data.plant.flower_meaning)


                                        plant_explanation = it.data.plant.explanation
                                        plant_modify = it.data.plant.modifier
                                        plant_mean = it.data.plant.flower_meaning
                                        plant_url = it.data.plant.image_url


                                    }
                            }
                        }
                    )
            }
            progressON()
            Handler(Looper.getMainLooper()).postDelayed({
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fragment_enroll,
                    ResultPlantFragment().apply {
                        arguments = Bundle().apply {
                            //putString("plantkey", binding.waterAlarmWeek.text.toString())
                            putString(
                                "plant_explanation",
                                plant_explanation
                            )
                            putString(
                                "plant_modify",
                                plant_modify
                            )
                            putString(
                                "plant_mean",
                                plant_mean
                            )
                            putString("plant_url", plant_url)

                        }
                    })
                progressOFF()
                transaction.addToBackStack(null)

                transaction.commit()

            }, 4000)

            //val intent = Intent(context, ResultPlantActivity::class.java)

            // intent.putExtra("plantkey", binding.waterAlarmWeek.text.toString())
            // startActivity(intent)

            //setFragment(ResultPlantFragment())


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