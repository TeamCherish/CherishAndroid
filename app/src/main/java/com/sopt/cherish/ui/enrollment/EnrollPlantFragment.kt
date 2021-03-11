package com.sopt.cherish.ui.enrollment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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

    lateinit var usernickname: String
    lateinit var userbirth: String
    var plant_explanation: String = ""
    var username: String = ""
    var plant_modify: String = ""
    var clockvalue: Boolean = false
    var weekvalue: Boolean = false


    var plant_mean: String = ""
    var plant_url: String = ""
    var plant_id:Int =0

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
        binding.editNick.hint = arguments?.getString("phonename")
        binding.phoneNumber.text = arguments?.getString("phonenumber")


/*
        binding.editNick.setOnFocusChangeListener { v, hasFocus ->
            binding.editNick.hint=""

            if(hasFocus==false){
                binding.editNick.hint=arguments?.getString("phonename")

            }
        }*/

        // binding.phoneName.text = arguments?.getString("phonename")


// 생일 빼고 사용자가 나머지 다 입력 시 버튼 활성화
        binding.detailOkBtn.isEnabled = false

        //binding.detailOkBtn.setBackgroundColor(R.color.cherish_green_main)
        binding.waterAlarmTime.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    clockvalue = true
                    if (clockvalue == true && weekvalue == true) {
                        binding.detailOkBtn.isEnabled = true
                        Log.d("clockvalue", binding.detailOkBtn.isClickable.toString())

                        binding.detailOkBtn.setBackgroundColor(Color.parseColor("#1AD287"))
                        binding.detailOkBtn.setTextColor(Color.parseColor("#ffffff"))

                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
        binding.waterAlarmWeek.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    weekvalue = true
                    if (clockvalue == true && weekvalue == true) {
                        binding.detailOkBtn.isEnabled = true

                        binding.detailOkBtn.setBackgroundColor(R.color.cherish_green_main)
                        Log.d("weekvalue", binding.detailOkBtn.isClickable.toString())

                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })



        binding.detailOkBtn.setOnClickListener {
            //  progressON()


            Log.d("username", arguments?.getString("phonename").toString())
//이름
            if (binding.editNick.text.isEmpty()) {
                usernickname = binding.editNick.hint.toString()
                username = binding.editNick.hint.toString()
            } else {
                usernickname = binding.editNick.text.toString()
                username = arguments?.getString("phonename").toString()
            }
            Log.d("usernickname", binding.editNick.text.toString())
//애칭

            if (binding.editBirth.text.isNotEmpty()) {
                //userbirth = binding.editBirth.text.substring(0,4)+"-"+binding.editBirth.text.substring(4,6)+"-"+binding.editBirth.text.substring(6,)
                userbirth = binding.editBirth.text.toString()
            } else {
                userbirth = "0000/00/00"

            }
            Log.d("userbirth", userbirth.toString())
//생일
            val userphone = arguments?.getString("phonenumber")
            Log.d("userphone", userphone.toString())
//번호
           /* val userphonebook = userphone?.substring(0, 3) + "-" + userphone?.substring(
                3,
                7
            ) + "-" + userphone?.substring(7)*/
           // Log.d("userphonebook", userphonebook.toString())
//번호나눈거

            val userwater = binding.waterAlarmWeek.text.toString()
            Log.d("userwater", binding.waterAlarmWeek.text.toString())
            //알람 주기
            //user_water = userwater.substring(6, 7).toInt()
            if (userwater.substring(8) == "month") {
                user_water = userwater.substring(6, 7).toInt() * 30
                Log.d("userwater2", user_water.toString())
            } else if (userwater.substring(8) == "week") {
                user_water = (userwater.substring(6, 7).toInt()) * 7
                Log.d("userwater2", user_water.toString())
            } else {
                user_water = userwater.substring(6, 7).toInt()
                Log.d("userwater2", user_water.toString())
            }

            val usertime = binding.waterAlarmTime.text.toString()
            val usertime_hour = usertime.substring(0, 5).toString()
            Log.d("usertime", usertime_hour.toString())

            val userid = arguments?.getInt("useridend").toString()
            Log.d("enrollplnatfrgment", userid.toString())
            val body =
                RequestEnrollData(
                    name = username.toString(),
                    nickname = usernickname,
                    birth = userbirth,
                    phone = userphone!!,
                    cycle_date = user_water,
                    notice_time = usertime_hour,
                    water_notice = true,
                    UserId = userid.toInt()

                )

            if (body != null) {
                requestData.enrollAPI.enrollCherish(body)
                    .enqueue(
                        object : Callback<ResponseEnrollData> {
                            override fun onFailure(
                                call: Call<ResponseEnrollData>,
                                t: Throwable
                            ) {
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

                                        Log.d(
                                            "data success!enroll",
                                            it.data.plant.flower_meaning
                                        )

                                        plant_id=it.data.plant.PlantStatusId
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
                            putInt("plant_id",plant_id)

                        }
                    })
                progressOFF()
                transaction.addToBackStack(null)

                transaction.commit()

            }, 4000)
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