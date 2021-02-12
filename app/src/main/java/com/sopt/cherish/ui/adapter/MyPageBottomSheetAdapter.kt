package com.sopt.cherish.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.MyPageCherryItemBinding
import com.sopt.cherish.remote.api.MyPageCherishData

class MyPageBottomSheetAdapter(private var data:MutableList<MyPageCherishData>) :
    RecyclerView.Adapter<MyPageBottomSheetAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: MyPageCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(cherishData: MyPageCherishData) {
            binding.mypageCherryNickname.text = cherishData.nickName
            binding.mypageCherryName.text = cherishData.name
            binding.mypageCherryLevel.text = "Lv. " + cherishData.level.toString()

            //초록색
            if(cherishData.dDay<0){
                binding.myPageDDay.text = "D" + cherishData.dDay.toString()
                binding.myPageDDay.setBackgroundResource(R.drawable.my_page_chip_green) //초록색으로
                binding.myPageDDay.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_green_main
                    )
                )
            }
            else if(cherishData.dDay==0){
                binding.myPageDDay.text = "D-day"
            }
            else{ //양수(빨간색): 그대로
                binding.myPageDDay.text = "D+" + cherishData.dDay.toString()
            }

            Glide.with(binding.root.context).load(cherishData.thumbnailImageUrl)
                .into(binding.mypageUserImg)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MyPageCherryItemBinding =
            MyPageCherryItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    fun update() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}