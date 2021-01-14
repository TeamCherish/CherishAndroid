package com.sopt.cherish.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            binding.mypageCherryLevel.text = "Lv. "+cherishData.level
            binding.myPageDDay.text = "D-"+cherishData.dDay.toString()
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

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}