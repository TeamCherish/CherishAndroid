package com.sopt.cherish.ui.main.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSettingBinding
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.main.MainViewModel

/**
 * 환경 설정 뷰
 */
class SettingFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSettingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        binding.btnQuestion.setOnClickListener {
            //setFragment(SettingAlarmFragment())
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Co.Cherishteam@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "체리쉬 문의")
            intent.putExtra(Intent.EXTRA_TEXT, "1. 문의 유형(문의, 버그 제보, 기타) :\n" +
                    "2. 회원 닉네임(필요시 기입) :\n" +
                    "3. 문의 내용 :\n" +
                    "\n" +
                    "문의하신 사항은 체리쉬팀이 신속하게 처리하겠습니다. 감사합니다 :)")
            intent.type = "message/rfc822"
            startActivity(intent)
        }

        return binding.root
    }

    fun setFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}