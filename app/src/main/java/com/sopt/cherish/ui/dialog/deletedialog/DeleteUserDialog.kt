package com.sopt.cherish.ui.dialog.deletedialog


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.MainApplication
import com.sopt.cherish.databinding.FragmentDeleteUserDialogBinding
import com.sopt.cherish.remote.api.RequestUserDeleteData
import com.sopt.cherish.remote.api.ResponseUserDeleteData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.splash.SplashActivity
import com.sopt.cherish.util.extension.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteUserDialog(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment() {

    private lateinit var binding: FragmentDeleteUserDialogBinding
    private val requestData = RetrofitBuilder
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view = inflater.inflate(layoutResId, container, false)
        binding = FragmentDeleteUserDialogBinding.bind(view)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        binding.buttonCopy.setOnClickListener {
            shortToast(requireContext(), "계정이 삭제되었습니다.")
            quitCherish()
            MainApplication.sharedPreferenceController.apply {
                deleteToken()
                deleteUserId()
                deleteUserPassword()
            }
            requireActivity().finish()
            val intent = Intent(requireActivity(), SplashActivity::class.java)
            startActivity(intent)
            dismiss()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    private fun quitCherish() {
        requestData.userDeleteAPI.deleteUser(
            RequestUserDeleteData(id = viewModel.cherishUserId.value!!)
        ).enqueue(
            object : Callback<ResponseUserDeleteData> {
                override fun onFailure(call: Call<ResponseUserDeleteData>, t: Throwable) {
                    Log.d("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseUserDeleteData>,
                    response: Response<ResponseUserDeleteData>
                ) {
                    response.takeIf {
                        it.isSuccessful
                    }?.body()
                        ?.let {
                        }
                }
            }
        )
    }
}