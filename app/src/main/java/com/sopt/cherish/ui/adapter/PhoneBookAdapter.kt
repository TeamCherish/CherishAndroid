package com.sopt.cherish.ui.adapter

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.ItemLayoutBinding


data class Phone(
    val id: String?, val name: String?, val phone: String?

)

// created by nayoung : 사용자 연락처들을 받아서 보여주는 adapter
class PhoneBookAdapter(private val phoneBookList: List<Phone>) :
    RecyclerView.Adapter<PhoneBookAdapter.Holder>() {
    var mStateButtons = SparseBooleanArray()


    var radiobutton: Boolean = true
    var checkedRadioButton: CompoundButton? = null
    lateinit var phonename: String
    lateinit var phonenumber: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemLayoutBinding = ItemLayoutBinding.inflate(layoutInflater, parent, false)

        if (binding.radioButton.isChecked) {
            checkedRadioButton = binding.radioButton

            radiobutton = true
        }

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.radioButton.isChecked = mStateButtons.get(position, false)


        holder.radioButton.setOnClickListener {
            if ( mStateButtons.get(position, false) ){
                mStateButtons.put(position, false);
                holder.radioButton.isChecked=false
            } else {
                mStateButtons.put(position, true);
                holder.radioButton.isChecked=true
            }

        }

        holder.radioButton.setOnCheckedChangeListener { compoundButton, isChecked ->




            checkedRadioButton?.apply { setChecked(!isChecked) }
            checkedRadioButton = compoundButton.apply {

                setChecked(isChecked)
                itemClickListner.onchange(radiobutton)

                radiobutton = true
            }

            if (isChecked) {
                radiobutton = true

                Log.d("phonebook", "${phoneBookList[position].name}")
                phonename = phoneBookList[position].name.toString()
                phonenumber = phoneBookList[position].phone.toString()
            }
        }
        val phone = phoneBookList[position]
        holder.setPhone(phone)
    }

    override fun getItemCount(): Int = phoneBookList.size

    override fun getItemId(position: Int): Long = position.toLong()

    inner class Holder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var mPhone: Phone? = null
        var radioButton = binding.radioButton



        fun setPhone(phone: Phone) {
            this.mPhone = phone
            binding.textName.text = phone.name
            binding.textPhone.text = phone.phone
        }
    }

    interface ItemClickListener {
        fun onchange(radio: Boolean)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }


}