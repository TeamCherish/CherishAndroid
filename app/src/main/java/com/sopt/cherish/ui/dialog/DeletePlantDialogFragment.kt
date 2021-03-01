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
import androidx.fragment.app.viewModels
import com.sopt.cherish.databinding.FragmentDeletePlantDialogBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.ResponseDeleteData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.main.manageplant.ManagePlantFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeletePlantDialogFragment(
    @LayoutRes
    private val layoutResId: Int, cherishid: Int
) : DialogFragment() {
    private val viewModel: MainViewModel by viewModels { Injection.provideMainViewModelFactory() }

    private lateinit var binding: FragmentDeletePlantDialogBinding
    private val requestData = RetrofitBuilder
    val deletecherish = cherishid
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(layoutResId, container, false)
        binding = FragmentDeletePlantDialogBinding.bind(view)
        Log.d("deleteid", deletecherish.toString())
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.buttonDeletePlant.setOnClickListener {

            requestData.deleteAPI.plantdelete(deletecherish)
                .enqueue(
                    object : Callback<ResponseDeleteData> {
                        override fun onFailure(call: Call<ResponseDeleteData>, t: Throwable) {
                            Log.d("통신 실패", t.toString())
                        }

                        override fun onResponse(
                            call: Call<ResponseDeleteData>,
                            response: Response<ResponseDeleteData>
                        ) {
                            Log.d("success", response.body().toString())
                            response.takeIf {
                                it.isSuccessful
                            }?.body()
                                ?.let { it ->

                                    Log.d("data success_delete", it.success.toString())
                                    viewModel.fetchUsers()
                                    parentFragmentManager.beginTransaction()
                                        .detach(ManagePlantFragment()).attach(ManagePlantFragment())
                                        .commit()
                                    activity?.finish()
                                    dismiss()
                                }
                        }
                    }
                )


        }


        // testDialogFragmentListener = activity as TestDialogFragmentListener


        return binding.root


    }
}