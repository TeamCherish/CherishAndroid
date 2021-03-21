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
import com.sopt.cherish.ui.dialog.BirthPickerDialogFragment
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.DeletePlantDialogFragment
import com.sopt.cherish.ui.dialog.ModifyWeekAlertFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EnrollModifyPlantFragment : Fragment() {
    private val requestData = RetrofitBuilder
    var modifycherish = 0
    var userid = 0
    lateinit var binding: FragmentEnrollModifyPlantBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val view = inflater.inflate(R.layout.fragment_enroll_modify_plant, container, false)
        binding = FragmentEnrollModifyPlantBinding.bind(view)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        modifycherish = arguments?.getInt("cherishid_modify")!!
        setModifyView()
        setListener()
    }

    fun setModifyView() {
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
                                binding.editNick.hint = it.data.cherishDetail.nickname.toString()
                                if (it.data.cherishDetail.birth == "Invalid Date") {
                                    binding.editBirth.hint = "00/00"
                                } else {
                                    binding.editBirth.hint =
                                        it.data.cherishDetail.birth.split("-")[1] + "/" +
                                                it.data.cherishDetail.birth.split("-")[2]

                                }
                                binding.waterAlarmWeek.text =
                                    "Every " + it.data.cherishDetail.cycle_date.toString() + " day"

                                if (it.data.cherishDetail.notice_time.split(":")[0].toInt() < 12) {
                                    binding.waterAlarmTime.text = it.data.cherishDetail.notice_time
                                } else {
                                    binding.waterAlarmTime.text = it.data.cherishDetail.notice_time
                                }

                                binding.phoneNumber.text = it.data.cherishDetail.phone


                            }
                    }
                })
    }

    fun setListener() {
        binding.editclock.setOnClickListener {
            val needWaterDialog = ClockPickerDialogFragment(R.layout.clockpicker_layout).show(
                parentFragmentManager,
                "MainActivity"
            )

        }

        binding.editweek.setOnClickListener {

            ModifyWeekAlertFragment(R.layout.fragment_modify_week_alert).show(
                parentFragmentManager,
                "modify"
            )


        }
        binding.editBirth.setOnClickListener {
            BirthPickerDialogFragment(R.layout.birthpicker_layout).show(
                parentFragmentManager,
                "modify"
            )
        }
        binding.detailOkBtnModify.setOnClickListener {
            //수정 버튼을 눌렀을 때

            var nickname_modify = binding.editNick.text.toString()

            if (nickname_modify == "") {
                nickname_modify = binding.editNick.hint.toString()
            }

            var birth_modify = binding.editBirth.text.toString()
            if (binding.editBirth.text.isEmpty()) {
                birth_modify = binding.editBirth.hint.toString()
            }

            val userwater = binding.waterAlarmWeek.text.split(" ")[1].toInt()

            val usertime = binding.waterAlarmTime.text.toString()

            var body = RequestModifyData(
                nickname = nickname_modify,
                birth = birth_modify,
                cycle_date = userwater,
                notice_time = usertime,
                water_notice = true,
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