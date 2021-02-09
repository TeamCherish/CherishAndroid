package com.sopt.cherish.ui.enrollment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentEnrollModifyPlantBinding
import com.sopt.cherish.remote.api.RequestUserinfoData
import com.sopt.cherish.remote.api.ResponseUserinfoData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.DeletePlantDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EnrollModifyPlantFragment() : Fragment() {
    private val requestData = RetrofitBuilder

    var modifycherish = 0
    var userid=0
    lateinit var binding: FragmentEnrollModifyPlantBinding


    lateinit var nick:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)


        val view = inflater.inflate(R.layout.fragment_enroll_modify_plant, container, false)
        binding = FragmentEnrollModifyPlantBinding.bind(view)
        userid= arguments?.getInt("cherishidgo_userid")!!
        modifycherish= arguments?.getInt("cherishidgo_delete")!!

        Log.d("modifycherishid", modifycherish.toString())
        Log.d("modifyuserid", userid.toString())


        //보여지는 부분
        val body=RequestUserinfoData(CherishId = modifycherish)
        requestData.userinfoAPI.getUserInfo(userid,body)
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
                                binding.editNick.hint=it.data.userDetail.nickname.toString()
                                binding.editBirth.hint=it.data.userDetail.birth
                                binding.waterAlarmWeek.text=it.data.cherishDetail.cycle_date.toString()
                                binding.waterAlarmTime.text=it.data.cherishDetail.notice_time
                                binding.phoneNumber.text=it.data.userDetail.phone


                            }}})

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