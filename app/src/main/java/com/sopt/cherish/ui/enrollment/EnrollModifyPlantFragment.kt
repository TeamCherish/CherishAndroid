package com.sopt.cherish.ui.enrollment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.ui.datail.DetailPlantActivity
import com.sopt.cherish.ui.datail.calendar.CalendarFragment
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.DeletePlantDialogFragment


class EnrollModifyPlantFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_enroll_modify_plant, container, false)
        return view
    }


    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 상세 입력")

        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.getItem(0).isVisible = false //disable menuitem 5
        menu.getItem(1).isVisible = false // invisible menuitem 2
        menu.getItem(2).isVisible = true // invisible menuitem 2
        (activity as DetailPlantActivity).invalidateOptionsMenu()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.onBackPressed()

            }
            R.id.trash -> {
                val deletedialog =
                    DeletePlantDialogFragment(R.layout.fragment_delete_plant_dialog).show(
                        parentFragmentManager, "asdf"
                    )
                /*val dialog = AlertDialog.Builder(context).create()
                val edialog: LayoutInflater = LayoutInflater.from(context)
                val mView: View = edialog.inflate(R.layout.fragment_delete_plant_dialog, null)
                *//* val close: Button = mView.findViewById(R.id.close_btn)
                 close.setOnClickListener {
                     dialog.dismiss()
                     dialog.cancel()
                 }*//*

                val color = ColorDrawable(Color.TRANSPARENT)
                // Dialog 크기 설정
                val inset = InsetDrawable(color, 85)
                dialog.window?.setBackgroundDrawable(inset)
                dialog.setCancelable(true)
                dialog.setView(mView)
                // dialog.create()
                dialog.show()
                //  dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

                return true*/

            }

        }

        return super.onOptionsItemSelected(item)
    }

}