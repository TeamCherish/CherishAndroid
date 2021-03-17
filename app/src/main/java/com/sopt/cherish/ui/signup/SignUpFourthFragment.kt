package com.sopt.cherish.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.style.URLSpan
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpFourthBinding
import com.sopt.cherish.remote.api.RequestSignUpData
import com.sopt.cherish.remote.api.ResponseSignUpData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.signin.SignInActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpFourthFragment : Fragment() {
    lateinit var binding: FragmentSignUpFourthBinding
    var nickName: String = ""
    var email: String = ""
    var password: String = ""
    var phone: String = ""
    var sex: Boolean = true
    var birth: String = ""
    private val requestData = RetrofitBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_fourth, container, false)

        binding = FragmentSignUpFourthBinding.bind(view)

        val bundle = (activity as SignUpActivity).mBundle

        email = bundle.getString("email").toString()
        password = bundle.getString("password").toString()
        phone = bundle.getString("phone").toString()
        sex = bundle.getBoolean("sex")
        birth = bundle.getString("birth").toString()

        Log.d("final", sex.toString())

        initializeLink()
        initializeNickName()

        return view
    }

    private fun initializeLink() {
        val transform =
            Linkify.TransformFilter(object : Linkify.TransformFilter, (Matcher, String) -> String {
                override fun transformUrl(match: Matcher?, url: String?): String {
                    return ""
                }

                override fun invoke(p1: Matcher, p2: String): String {
                    return ""
                }
            })

        val personalSecurity = Pattern.compile("개인정보보호 정책")
        val service = Pattern.compile("서비스 이용 약관")
        Linkify.addLinks(
            binding.serviceText,
            personalSecurity,
            "https://www.notion.so/Cherish-2d35c1bffa2f4d49943db302d76e3cac",
            null,
            transform
        )
        Linkify.addLinks(
            binding.serviceText,
            service,
            "https://www.notion.so/Cherish-d96f88172ffa4d80b257665849bddc65",
            null,
            transform
        )

        binding.serviceText.removeUnderline()
    }

    private fun TextView.removeUnderline() {
        val spannable = SpannableString(text)
        for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
            spannable.setSpan(object : URLSpan(u.url) {
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }, spannable.getSpanStart(u), spannable.getSpanEnd(u), 0)
        }
        text = spannable
    }

    private fun initializeNickName() {
        binding.userNickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                nickName = binding.userNickname.text.toString()


                if (nickName.length <= 8) {
                    binding.isUsableNickname.visibility=View.VISIBLE
                    binding.isUsableNickname.text = "사용하실 수 있는 닉네임입니다."
                    binding.isUsableNickname.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_green_main
                        )
                    )

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

                    binding.signUpButton.setOnClickListener {
                        requestServer()
                    }
                } else {
                    binding.isUsableNickname.text = "사용하실 수 없는 닉네임입니다."
                    binding.isUsableNickname.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_pink_sub
                        )
                    )

                    //버튼 비활성화
                    binding.signUpButton.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_text_box_gray
                        )
                    )
                    binding.signUpButton.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_text_gray
                        )
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun requestServer() {
        Log.d("final email", email)
        Log.d("final password", password)
        Log.d("nickname", nickName)
        Log.d("phpone", phone)
        Log.d("sex", sex.toString())
        Log.d("birth", birth)

        requestData.signUpAPI.postSignUp(
            RequestSignUpData(
                email = email,
                password = password,
                nickname = nickName,
                phone = phone,
                sex = sex.toString(),
                birth = birth
            )
        ).enqueue(
            object : Callback<ResponseSignUpData> {
                override fun onFailure(call: Call<ResponseSignUpData>, t: Throwable) {
                    Log.d("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseSignUpData>,
                    response: Response<ResponseSignUpData>
                ) {
                    response.takeIf {
                        it.isSuccessful
                    }?.body()
                        ?.let { it ->
                            Log.d("success", it.success.toString())
                            val intent = Intent(context, SignInActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                }
            }
        )
    }

}