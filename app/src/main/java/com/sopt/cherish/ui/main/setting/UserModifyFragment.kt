package com.sopt.cherish.ui.main.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentUserModifyBinding
import com.sopt.cherish.remote.api.RequestNicknameData
import com.sopt.cherish.remote.api.ResponseNicknameChangedata
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserModifyFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    lateinit var binding: FragmentUserModifyBinding
    private val requestData = RetrofitBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_user_modify, container, false)

        binding = FragmentUserModifyBinding.bind(view)

        binding.constraintLayoutSettingBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.settingEditNickname.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    binding.buttonNickchange.setBackgroundResource(R.color.cherish_green_main)
                }
            })

        binding.settingmodifycancel.setOnClickListener {
            binding.settingEditNickname.setText("")
        }
        binding.buttonNickchange.setOnClickListener {
            val body = RequestNicknameData(
                viewModel.cherishuserId.value!!,
                binding.settingEditNickname.text.toString()
            )
            requestData.nicknameChangeAPI.nicknamechange(body)
                .enqueue(
                    object : Callback<ResponseNicknameChangedata> {
                        override fun onFailure(
                            call: Call<ResponseNicknameChangedata>,
                            t: Throwable
                        ) {
                            Log.d("통신 실패", t.toString())
                        }

                        override fun onResponse(
                            call: Call<ResponseNicknameChangedata>,
                            response: Response<ResponseNicknameChangedata>
                        ) {
                            Log.d("success", response.body().toString())
                            response.takeIf {
                                it.isSuccessful
                            }?.body()
                                ?.let { it ->

                                    Log.d(" 닉네임 변경 오나료 ", it.success.toString())
                                    binding.settingEditNickname.text =
                                        binding.settingEditNickname.text
                                    // binding.settingEditEmail.text=binding.settingEditNickname.text

                                }
                        }
                    }
                )
            activity?.onBackPressed()

        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var usernick = arguments?.getString("settingusernickname").toString()
        binding.settingEditNickname.hint = (usernick)

        binding.settingEditEmail.hint = (arguments?.getString("settinguseremail"))
        binding.settingEditEmail.isEnabled = false
    }


}