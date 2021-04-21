package com.sopt.cherish.ui.dialog.pwfinding

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.databinding.FragmentIdFindingDialogBinding

class IDFindingDialog(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment() {

    private lateinit var binding: FragmentIdFindingDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view = inflater.inflate(layoutResId, container, false)
        binding = FragmentIdFindingDialogBinding.bind(view)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        binding.buttonCopy.setOnClickListener {
            //클립보드 사용 코드
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("mail", "co.cherishteam@gmail.com")
            clipboard.setPrimaryClip(clipData)

            Toast.makeText(context, "메일 주소가 복사되었습니다.", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }
}