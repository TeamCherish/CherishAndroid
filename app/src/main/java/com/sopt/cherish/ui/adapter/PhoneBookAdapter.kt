package com.sopt.cherish.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.ActivityPhonebookBinding
import com.sopt.cherish.databinding.ItemLayoutBinding
import com.sopt.cherish.ui.enrollment.EnrollPlantActicity
import kotlin.coroutines.coroutineContext


data class Phone(
        val id: String?, val name: String?, val phone: String?

)
// created by nayoung : 사용자 연락처들을 받아서 보여주는 adapter
class PhoneBookAdapter(val PhoneBooklist: List<Phone>) : RecyclerView.Adapter<PhoneBookAdapter.Holder>() {



    private var checkedRadioButton: CompoundButton? = null
    lateinit var phonename:String
    lateinit var phonenumber:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {


        val layoutInflater =LayoutInflater.from(parent.context)

        val binding: ItemLayoutBinding= ItemLayoutBinding.inflate(layoutInflater, parent, false)

        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        //binding.radioButton.setOnCheckedChangeListener(checkedChangeListener)

        if (binding.radioButton.isChecked) checkedRadioButton = binding.radioButton

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.radioButton.setOnCheckedChangeListener { compoundButton, isChecked ->

            checkedRadioButton?.apply { setChecked(!isChecked) }
            checkedRadioButton = compoundButton.apply { setChecked(isChecked)}

            if(isChecked){
                Log.d("asdf", "${PhoneBooklist[position].name}")
                phonename= PhoneBooklist[position].name.toString()
                phonenumber= PhoneBooklist[position].phone.toString()

            }

        }
        val phone = PhoneBooklist[position]
        holder.setPhone(phone)
    }



    override fun getItemCount(): Int {
        return PhoneBooklist.size

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("MissingPermission")
    inner class Holder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var mPhone: Phone? = null
        val radioButton =binding.radioButton

        init {



        }

        fun setPhone(phone: Phone) {
            this.mPhone = phone
            binding.textName.text = phone.name
            binding.textPhone.text = phone.phone
        }


    }
}