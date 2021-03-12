package com.sopt.cherish.ui.pwfinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPwFindingFirstBinding
import com.sopt.cherish.ui.signup.SignUpActivity

class PwFindingFirstFragment : Fragment() {

    lateinit var binding:FragmentPwFindingFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pw_finding_first, container, false)

        binding = FragmentPwFindingFirstBinding.bind(view)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as SignUpActivity).setActionBarTitleSignUp("비밀번호 찾기")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}