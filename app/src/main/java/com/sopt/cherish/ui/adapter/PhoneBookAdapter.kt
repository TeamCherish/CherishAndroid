package com.sopt.cherish.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ItemLayoutBinding
import kotlinx.android.synthetic.main.item_layout.view.*


data class Phone(val id:String?, val name:String?, val phone:String?

)
class PhoneBookAdapter (val list2: List<Phone>): RecyclerView.Adapter<PhoneBookAdapter.Holder>() {

    private lateinit var binding: ItemLayoutBinding

    private var checkedRadioButton: CompoundButton? = null
    interface OnSingleClickListener
    {
        fun send(month: Phone)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.radioButton.setOnCheckedChangeListener(checkedChangeListener)
        if (holder.itemView.radioButton.isChecked) checkedRadioButton = holder.itemView.radioButton
        val phone = list2[position]
        holder.setPhone(phone)
    }
    private val checkedChangeListener = CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
        checkedRadioButton?.apply { setChecked(!isChecked) }
        checkedRadioButton = compoundButton.apply { setChecked(isChecked) }
    }
    override fun getItemCount(): Int {
        return list2.size

    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    @SuppressLint("MissingPermission")
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPhone:Phone? = null
        var chkBox  = itemView.findViewById(R.id.radioButton) as RadioButton

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

        fun setPhone(phone:Phone) {
            this.mPhone = phone
            itemView.textName.text = phone.name
            itemView.textPhone.text = phone.phone
        }


    }
}


