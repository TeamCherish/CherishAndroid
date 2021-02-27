package com.sopt.cherish.ui.dialog.wateringdialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.DialogWateringBinding
import com.sopt.cherish.ui.dialog.delaywatering.DelayWateringDialogFragment
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.DialogUtil

/**
 * Created on 01-03 by SSong-develop
 * popUp_Watering
 * todo : sizing 하는 것만 다시 한번 좀 생각해보면 좋을거 같음 이건 끝!
 */

class WateringDialogFragment : DialogFragment(), View.OnClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DialogWateringBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_watering, container, false)
        binding.dialogWatering = this
        binding.mainViewModel = viewModel
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    fun navigateContact() {
        parentFragmentManager.let { fm -> ContactDialogFragment().show(fm, TAG) }
        dismiss()
    }

    fun navigateNextTimeContact() {
        DelayWateringDialogFragment().show(parentFragmentManager, TAG)
        dismiss()
    }

    override fun onClick(view: View?) {
        // dialog 주변을 클릭하면 dismiss
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.875f, 0.57f)
    }

    companion object {
        const val TAG = "WateringDialog"
    }

}