package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.DialogWateringBinding
import com.sopt.cherish.util.DialogUtil

/**
 * Created on 01-03 by SSong-develop
 * popUp_Watering
 */

class WateringDialogFragment : DialogFragment(), View.OnClickListener {
    private val TAG = "WateringDialog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_watering, container, false)
        val binding = DialogWateringBinding.bind(view)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.wateringAcceptBtn.setOnClickListener {
            navigateContact()
        }

        binding.wateringNextTimeBtn.setOnClickListener {
            navigateNextTimeContact()
        }

        return binding.root
    }

    private fun navigateContact() {
        parentFragmentManager.let { fm -> ContactDialogFragment().show(fm, TAG) }
        dismiss()
    }

    private fun navigateNextTimeContact() {
        dismiss()
    }

    override fun onClick(view: View?) {
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.875f, 0.542f)
    }
}