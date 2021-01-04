package com.sopt.cherish.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ItemLayoutBinding



data class Phone(val id: String?, val name: String?, val phone: String?

)

class PhoneBookAdapter(val PhoneBooklist: List<Phone>) : RecyclerView.Adapter<PhoneBookAdapter.Holder>() {

    private lateinit var binding: ItemLayoutBinding

    private var checkedRadioButton: CompoundButton? = null

    interface OnSingleClickListener {
        fun send(month: Phone)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater =LayoutInflater.from(parent.context)
        val binding: ItemLayoutBinding= ItemLayoutBinding.inflate(layoutInflater,parent,false)
       // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        binding.radioButton.setOnCheckedChangeListener(checkedChangeListener)
        if (binding.radioButton.isChecked) checkedRadioButton = binding.radioButton
        val phone = PhoneBooklist[position]
        holder.setPhone(phone)
    }

    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
        checkedRadioButton?.apply { setChecked(!isChecked) }
        checkedRadioButton = compoundButton.apply { setChecked(isChecked) }
    }

    override fun getItemCount(): Int {
        return PhoneBooklist.size

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("MissingPermission")
    inner class Holder( private val binding:ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var mPhone: Phone? = null

        init {
/*
            itemView.btnPhone.setOnClickListener {
                mPhone?.phone.let { phoneNumber ->
                    val uri = Uri.parse("tel:${phoneNumber.toString()}")
                    val intent = Intent(Intent.ACTION_CALL, uri)
                    itemView.context.startActivity(intent)
                }
            }*/

        }

        fun setPhone(phone: Phone) {
            this.mPhone = phone
            binding.textName.text = phone.name
            binding.textPhone.text = phone.phone
        }


    }
}


