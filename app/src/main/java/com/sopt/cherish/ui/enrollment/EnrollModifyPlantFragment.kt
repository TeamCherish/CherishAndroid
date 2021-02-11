package com.sopt.cherish.ui.enrollment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentEnrollModifyPlantBinding
import com.sopt.cherish.remote.api.RequestModifyData
import com.sopt.cherish.remote.api.ResponseModifyData
import com.sopt.cherish.remote.api.ResponseUserinfoData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.DeletePlantDialogFragment
import com.sopt.cherish.ui.dialog.WeekPickerDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EnrollModifyPlantFragment : Fragment() {
    private val requestData = RetrofitBuilder

    var modifycherish = 0
    var userid = 0
    lateinit var binding: FragmentEnrollModifyPlantBinding

    var week_modify = 0
    lateinit var nick: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)


        val view = inflater.inflate(R.layout.fragment_enroll_modify_plant, container, false)
        binding = FragmentEnrollModifyPlantBinding.bind(view)
        // userid= arguments?.getInt("cherishidgo_userid")!!


        // 식물카드에서 체리쉬 아이디 가져오기
        modifycherish = arguments?.getInt("cherishid_modify")!!

        Log.d("modifycherishid", modifycherish.toString())
        Log.d("modifyuserid", userid.toString())


        //보여지는 부분
        requestData.userinfoAPI.getUserInfo(modifycherish)
            .enqueue(
                object : Callback<ResponseUserinfoData> {
                    override fun onFailure(call: Call<ResponseUserinfoData>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseUserinfoData>,
                        response: Response<ResponseUserinfoData>
                    ) {
                        Log.d("식물수정", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->
                                binding.editNick.hint = it.data.userDetail.nickname.toString()
                                binding.editBirth.hint = it.data.userDetail.birth
                                binding.waterAlarmWeek.text =
                                    it.data.cherishDetail.cycle_date.toString()
                                binding.waterAlarmTime.text = it.data.cherishDetail.notice_time
                                binding.phoneNumber.text = it.data.userDetail.phone


                            }
                    }
                })


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
        binding.detailOkBtnModify.setOnClickListener {
            //수정 버튼을 눌렀을 때

            var nickname_modify=binding.editNick.text.toString()
            Log.d("nickname_modify", nickname_modify.toString())

           if(nickname_modify==""){
               nickname_modify=binding.editNick.hint.toString()
           }

            var birth_modify=binding.editBirth.text.toString()
            if(birth_modify==""){
                birth_modify=binding.editBirth.hint.substring(0,4)+"-"+
                        binding.editBirth.hint.substring(4,6)+"-"+binding.editBirth.hint.substring(6,).toString()
            }else{
                birth_modify= binding.editBirth.text.substring(0,4)+"-"+
                        binding.editBirth.text.substring(4,6)+"-"+binding.editBirth.text.substring(6,).toString()
            }

            val userwater = binding.waterAlarmWeek.text.toString()
            Log.d("userwater", binding.waterAlarmWeek.text.toString())
            //알람 주기

            //user_water = userwater.substring(6, 7).toInt()
            if (userwater.substring(8) == "month") {
                week_modify = userwater.substring(6, 7).toInt() * 30
                Log.d("userwater2", week_modify.toString())
            } else if (userwater.substring(8) == "week") {
                week_modify = (userwater.substring(6, 7).toInt()) * 7
                Log.d("userwater2", week_modify.toString())
            } else {
                week_modify = userwater.substring(6, 7).toInt()
                Log.d("userwater2", week_modify.toString())
            }

            var week_switch_modify = binding.alarmSwitch.isChecked

            val usertime = binding.waterAlarmTime.text.toString()
            val usertime_hour_modify = usertime.substring(0, 5).toString()
            Log.d("modifycherish", modifycherish.toString())
            Log.d("birth_modify", birth_modify.toString())

            var body = RequestModifyData(
                nickname = nickname_modify,
                birth = birth_modify,
                cycle_date = week_modify,
                notice_time = usertime_hour_modify,
                water_notice = week_switch_modify,
                id = modifycherish
            )
            requestData.modifyAPI.plantmodify(body).enqueue(
                object : Callback<ResponseModifyData> {
                    override fun onFailure(
                        call: Call<ResponseModifyData>,
                        t: Throwable
                    ) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseModifyData>,
                        response: Response<ResponseModifyData>
                    ) {
                        Log.d("success", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->


                                Log.d("수정완료", response.body().toString())
                                activity?.onBackPressed()

                            }
                    }
                }
            )
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 상세 입력")

        }
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.getItem(0).isVisible = false //disable menuitem 5
        menu.getItem(1).isVisible = false // invisible menuitem 2
        menu.getItem(2).isVisible = true // invisible menuitem 2
        (activity as DetailPlantActivity).invalidateOptionsMenu()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true


            }
            R.id.trash -> {
                val deletedialog =
                    DeletePlantDialogFragment(
                        R.layout.fragment_delete_plant_dialog,
                        modifycherish
                    ).show(
                        parentFragmentManager, "asdf"
                    )
                return true
                /*val dialog = AlertDialog.Builder(context).create()
                val edialog: LayoutInflater = LayoutInflater.from(context)
                val mView: View = edialog.inflate(R.layout.fragment_delete_plant_dialog, null)
                *//* val close: Button = mView.findViewById(R.id.close_btn)
                 close.setOnClickListener {
                     dialog.dismiss()
                     dialog.cancel()
                 }*//*

                val color = ColorDrawable(Color.TRANSPARENT)
                // Dialog 크기 설정
                val inset = InsetDrawable(color, 85)
                dialog.window?.setBackgroundDrawable(inset)
                dialog.setCancelable(true)
                dialog.setView(mView)
                // dialog.create()
                dialog.show()
                //  dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

                return true*/

            }

        }

        return super.onOptionsItemSelected(item)
    }

}