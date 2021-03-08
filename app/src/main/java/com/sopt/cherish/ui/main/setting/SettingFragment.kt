package com.sopt.cherish.ui.main.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.MainApplication
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSettingBinding
import com.sopt.cherish.remote.api.MyPageUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.extension.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 환경 설정 뷰
 */
class SettingFragment : Fragment() {

    private val requestData = RetrofitBuilder
    private var usernickname: String = ""
    private var useremail: String = ""
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.mainViewModel = viewModel
        binding = FragmentSettingBinding.bind(view)

        setView()
        binding.constraintLayoutQuestion.setOnClickListener {
            //setFragment(SettingAlarmFragment())
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Co.Cherishteam@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "체리쉬 문의")
            intent.putExtra(
                Intent.EXTRA_TEXT, "1. 문의 유형(문의, 버그 제보, 기타) :\n" +
                        "2. 회원 닉네임(필요시 기입) :\n" +
                        "3. 문의 내용 :\n" +
                        "\n" +
                        "문의하신 사항은 체리쉬팀이 신속하게 처리하겠습니다. 감사합니다 :)"
            )
            intent.type = "message/rfc822"
            startActivity(intent)
        }

        binding.constraintLayoutInfo.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/Cherish-2d35c1bffa2f4d49943db302d76e3cac")
            )
            startActivity(intent)
        }

        binding.constraintLayoutService.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/Cherish-d96f88172ffa4d80b257665849bddc65")
            )
            startActivity(intent)
        }

        binding.settingNextNickname.setOnClickListener {
            setFragment(UserModifyFragment())
        }

        binding.settingAlarmSetting.setOnCheckedChangeListener { buttonView, isChecked ->
            MainApplication.sharedPreferenceController.setAlarmKey(isChecked)
        }

        binding.friendsCons.setOnClickListener {
            shortToast(requireContext(), "로그아웃 되었습니다.")
            MainApplication.sharedPreferenceController.apply {
                deleteToken()
                deleteUserId()
                deleteUserPassword()
            }
            requireActivity().finish()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingAlarmSetting.isChecked =
            MainApplication.sharedPreferenceController.getAlarmKey()
        setView()
    }

    fun setFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment.apply {
            arguments = Bundle().apply {
                putString("settingusernickname", usernickname)
                putString("settinguseremail", useremail)

            }
        })
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun setView() {
        requestData.myPageAPI.fetchUserPage(viewModel.cherishuserId.value!!)
            .enqueue(
                object : Callback<MyPageUserRes> {
                    override fun onFailure(call: Call<MyPageUserRes>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<MyPageUserRes>,
                        response: Response<MyPageUserRes>
                    ) {
                        Log.d("success", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->

                                Log.d("data success!", it.myPageUserData.waterCount.toString())
                                usernickname = it.myPageUserData.user_nickname
                                useremail = it.myPageUserData.email
                                Log.d("이메일", it.myPageUserData.email)
                                binding.settingUsernickname.text =
                                    it.myPageUserData.user_nickname.toString()
                                binding.settingUseremail.text = it.myPageUserData.email
                                Log.d("list", it.myPageUserData.result.toString())
                            }
                    }
                })
    }

}