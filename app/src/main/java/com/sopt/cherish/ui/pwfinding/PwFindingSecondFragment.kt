package com.sopt.cherish.ui.pwfinding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPwFindingSecondBinding
import com.sopt.cherish.remote.api.RequestPwFindingData
import com.sopt.cherish.remote.api.ResponsePwFindingData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.dialog.pwfinding.PwFindingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PwFindingSecondFragment : Fragment() {

    var email: String = ""
    lateinit var binding: FragmentPwFindingSecondBinding
    private val requestData = RetrofitBuilder
    private var certificationNumber: String = ""
    private var authdata = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val bundle = (activity as PwFindingActivity).mBundle
        email = bundle.getString("email").toString()

        val view = inflater.inflate(R.layout.fragment_pw_finding_second, container, false)

        binding = FragmentPwFindingSecondBinding.bind(view)

        getCertificationNumber()

        requestServer(email)

        binding.certificationAgain.setOnClickListener {
            requestServer(email)
        }

        return binding.root
    }

    private fun requestServer(email: String) {
        requestData.pwFindingAPI.postPwFinding(
            RequestPwFindingData(email = email)
        ).enqueue(
            object : Callback<ResponsePwFindingData> {
                override fun onFailure(call: Call<ResponsePwFindingData>, t: Throwable) {
                    Log.d("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponsePwFindingData>,
                    response: Response<ResponsePwFindingData>
                ) {
                    response.takeIf {
                        it.isSuccessful
                    }?.body()
                        ?.let { it ->
                            authdata = it.data.verifyCode
                        }
                }
            }
        )
    }

    private fun getCertificationNumber() {
        binding.identificationNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                certificationNumber = binding.identificationNumber.text.toString()

                binding.signUpButton.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_light_gray
                    )
                )
                binding.signUpButton.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_pass_text_gray
                    )
                )
                if (certificationNumber.length == 4) {
                    //버튼 초록색 활성화
                    binding.signUpButton.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_green_main
                        )
                    )
                    binding.signUpButton.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.white
                        )
                    )

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                certificationNumber = binding.identificationNumber.text.toString()

                binding.signUpButton.setOnClickListener {
                    if (certificationNumber == authdata.toString()) {
                        val bundle = Bundle()
                        bundle.putString("email", email)
                        (activity as PwFindingActivity).postData(bundle)
                        (activity as PwFindingActivity).replaceFragment(2)
                    } else {
                        val fm = requireActivity().supportFragmentManager
                        val dialogFragment = PwFindingDialog(R.layout.fragment_pw_finding_dialog)
                        dialogFragment.setTargetFragment(this@PwFindingSecondFragment, 101)
                        dialogFragment.show(fm, "PwFindingSecondFragment")
                    }
                }

            }
        })
    }
}