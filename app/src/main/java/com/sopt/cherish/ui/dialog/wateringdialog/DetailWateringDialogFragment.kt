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
import com.sopt.cherish.databinding.DialogDetailPlantWateringBinding
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.ui.dialog.DelayWateringDialogFragment
import com.sopt.cherish.util.DialogUtil

/**
 * Created on 01-03 by SSong-develop
 * popUp_Watering
 * 요것은 끝!
 */

class DetailWateringDialogFragment : DialogFragment(),
    View.OnClickListener {

    private val viewModel: DetailPlantViewModel by activityViewModels()

    // todo : DelayDialog도 하나 만들어야 합니다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DialogDetailPlantWateringBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_detail_plant_watering,
            container,
            false
        )
        binding.dialogDetailWatering = this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    fun navigateContact() {
        parentFragmentManager.let { fm ->
            DetailPlantContactDialogFragment().show(
                fm,
                TAG
            )
        }
        dismiss()
    }

    fun navigateNextTimeContact() {
        DelayWateringDialogFragment().show(parentFragmentManager, TAG)
        dismiss()
    }

    override fun onClick(view: View?) {
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.875f, 0.57f)
    }

    companion object {
        private const val TAG = "WateringDialog"
    }
}