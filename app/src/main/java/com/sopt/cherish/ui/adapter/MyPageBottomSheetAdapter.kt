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

class MyPageBottomSheetAdapter(private var context: Context, var data: List<MyPageCherishData>) :
    RecyclerView.Adapter<MyPageBottomSheetAdapter.MainViewHolder>() {

    class MainViewHolder(private var binding: MyPageCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(cherishData: MyPageCherishData) {
            binding.mypageCherryNickname.text = cherishData.nickName
            binding.mypageCherryName.text = cherishData.name
            binding.mypageCherryLevel.text = "Lv. " + cherishData.level
            if (cherishData.dDay.toString()[0] != '-')
                binding.myPageDDay.text = "D+" + cherishData.dDay.toString()
            else if (cherishData.dDay == 0)
                binding.myPageDDay.text = "D-day"
            else if (cherishData.dDay == 1)
                binding.myPageDDay.text = "D-" + cherishData.dDay.toString()
            else {
                binding.myPageDDay.text = "D" + cherishData.dDay.toString()
                binding.myPageDDay.setBackgroundResource(R.drawable.my_page_chip_green)
                binding.myPageDDay.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_green_main
                    )
                )
            }

            Glide.with(binding.root.context).load(cherishData.thumbnailImageUrl)
                .into(binding.mypageUserImg)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MyPageCherryItemBinding =
            MyPageCherryItemBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }
    fun update(){
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