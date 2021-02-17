package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.databinding.FragmentCheckPhoneDialogBinding
import com.sopt.cherish.databinding.FragmentDeletePlantDialogBinding
import com.sopt.cherish.remote.api.ResponseDeleteData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckPhoneDialogFragment(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment() {

    private lateinit var binding: FragmentCheckPhoneDialogBinding
    private val requestData = RetrofitBuilder



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(layoutResId, container, false)
        binding = FragmentCheckPhoneDialogBinding.bind(view)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }


        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        return binding.root


    }
}