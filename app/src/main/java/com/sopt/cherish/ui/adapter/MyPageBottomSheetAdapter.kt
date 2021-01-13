package com.sopt.cherish.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.MyPageCherryItemBinding
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.remote.api.MyPageUserData
import com.sopt.cherish.ui.domain.MyPageCherryLevelDataclass

class MyPageBottomSheetAdapter(private var context: Context, var data:List<MyPageCherishData>)
    : RecyclerView.Adapter<MyPageBottomSheetAdapter.MainViewHolder>() {

    class MainViewHolder(private var binding: MyPageCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cherishData: MyPageCherishData) {
            binding.mypageCherryNickname.text=cherishData.nickName
            binding.mypageCherryName.text=cherishData.name
            binding.mypageCherryLevel.text=cherishData.level
            binding.myPageDDay.text=cherishData.dDay.toString()
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
    }

    override fun getItemCount(): Int = data.size

}