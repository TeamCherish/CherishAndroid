package com.sopt.cherish.ui.pwfinding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPwFindingFirstBinding

class PwFindingFirstFragment : Fragment() {

    lateinit var binding: FragmentPwFindingFirstBinding
    var email: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pw_finding_first, container, false)

        binding = FragmentPwFindingFirstBinding.bind(view)

        checkEmail()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as PwFindingActivity).setActionBarTitlePwFinding("비밀번호 찾기")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun postEmail(email: String) {
        val bundle = Bundle()
        bundle.putString("email", email)
        (activity as PwFindingActivity).postData(bundle)
        (activity as PwFindingActivity).replaceFragment(1)
    }

    private fun checkEmail() {
        binding.userEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                email = binding.userEmail.text.toString()

                val flag = isEmailValid(email)

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

                if (flag) {
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
                        postEmail(email)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun isEmailValid(email: String): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }
}