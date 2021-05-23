package com.sopt.cherish.ui.dialog.selectgender

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.DialogSelectGenderBinding
import com.sopt.cherish.util.DialogUtil

class SelectGenderDialogFragment : DialogFragment() {

    private lateinit var binding: DialogSelectGenderBinding
    private val genders = arrayOf("여성", "남성")
    private var selectGender: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_select_gender, container, false)

        binding = DialogSelectGenderBinding.bind(view)


        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initializeGenderPicker(binding)

        binding.selectGenderAcceptBtn.setOnClickListener {
            postGender(binding)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.9f, 0.6f)
    }


    private fun initializeGenderPicker(binding: DialogSelectGenderBinding) {
        binding.selectGenderPicker.apply {
            wrapSelectorWheel = false
            maxValue = 1
            minValue = 0
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            displayedValues = genders
        }

        binding.selectGenderPicker.removeDivider()
    }

    private fun postGender(binding: DialogSelectGenderBinding) {
        selectGender = binding.selectGenderPicker.value
        val intent = Intent()
        intent.putExtra("gender", selectGender)
        targetFragment!!.onActivityResult(targetRequestCode, 101, intent)
        dialog?.dismiss()
    }

    private fun NumberPicker.removeDivider() {
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(Color.TRANSPARENT)
                    pf[this] = colorDrawable
                } catch (e: java.lang.IllegalArgumentException) {

                } catch (e: Resources.NotFoundException) {

                } catch (e: IllegalAccessException) {

                }
                break
            }
        }
    }

}