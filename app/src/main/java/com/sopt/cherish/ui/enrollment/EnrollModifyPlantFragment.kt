package com.sopt.cherish.ui.enrollment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentEnrollModifyPlantBinding
import com.sopt.cherish.ui.datail.DetailPlantActivity
import com.sopt.cherish.ui.dialog.DeletePlantDialogFragment


class EnrollModifyPlantFragment(cherish:Int) : Fragment() {

    var modifycherish=cherish
    lateinit var binding: FragmentEnrollModifyPlantBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)


        val view = inflater.inflate(R.layout.fragment_enroll_modify_plant, container, false)
        binding= FragmentEnrollModifyPlantBinding.bind(view)

        "cherishidgo_delete"












        return binding.root
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
                return true


            }
            R.id.trash -> {
                val deletedialog =
                    DeletePlantDialogFragment(R.layout.fragment_delete_plant_dialog,modifycherish).show(
                        parentFragmentManager, "asdf"
                    )
                return true
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