package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.URLSpan
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpFourthBinding
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpFourthFragment : Fragment() {
    lateinit var binding: FragmentSignUpFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_fourth, container, false)

        binding= FragmentSignUpFourthBinding.bind(view)

        initializeLink()

        return view
    }

    private fun initializeLink(){
        val transform=Linkify.TransformFilter(object:Linkify.TransformFilter,(Matcher, String)->String{
            override fun transformUrl(match: Matcher?, url: String?): String {
                return ""
            }

            override fun invoke(p1: Matcher, p2: String): String {
                return ""
            }
        })

        val personalSecurity= Pattern.compile("개인정보보호 정책")
        val service=Pattern.compile("서비스 이용 약관")
        Linkify.addLinks(binding.serviceText,personalSecurity,"https://www.notion.so/Cherish-2d35c1bffa2f4d49943db302d76e3cac",null,transform)
        Linkify.addLinks(binding.serviceText,service,"https://www.notion.so/Cherish-d96f88172ffa4d80b257665849bddc65",null,transform)

        binding.serviceText.removeUnderline()
    }

    private fun TextView.removeUnderline(){
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

}