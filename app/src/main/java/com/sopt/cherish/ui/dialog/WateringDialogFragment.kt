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
import com.sopt.cherish.util.AdjustDialog

class WateringDialogFragment : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialog_watering, container, false)
        val binding = DialogWateringBinding.bind(view)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.wateringAcceptBtn.setOnClickListener {

        }
        return binding.root
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        AdjustDialog(requireContext()).adjustSize(this, 0.875f, 0.5452f)
    }
}