package com.sopt.cherish.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.ItemLayoutPlantBinding

data class Phonemypage(
    val id: String?, val name: String?, val phone: String?

)

// created by nayoung : 사용자 연락처들을 받아서 보여주는 adapter
class MypagePhoneBookAdapter(private val phoneBookList: List<Phonemypage>) :
    RecyclerView.Adapter<MypagePhoneBookAdapter.Holder>() {

    var radiobutton: Boolean = true
    var checkedRadioButton: CompoundButton? = null
    lateinit var phonename: String
    lateinit var phonenumber: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemLayoutPlantBinding =
            ItemLayoutPlantBinding.inflate(layoutInflater, parent, false)


        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MypagePhoneBookAdapter.Holder, position: Int) {
        val phone = phoneBookList[position]
        holder.setPhone(phone)
    }

    override fun getItemCount(): Int = phoneBookList.size

    override fun getItemId(position: Int): Long = position.toLong()

    inner class Holder(private val binding: ItemLayoutPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var mPhone: Phonemypage? = null

        fun setPhone(phone: Phonemypage) {
            this.mPhone = phone
            binding.textName.text = phone.name
            binding.textPhone.text = phone.phone
        }
    }

    interface ItemClickListener {
        fun onchange(radio: Boolean)
        fun oncount(count: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }


}