package com.sopt.cherish.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.MyPageCherryItemBinding
import com.sopt.cherish.ui.domain.MyPageCherryLevelDataclass

class MyPageBottomSheetAdapter : RecyclerView.Adapter<MyPageBottomSheetAdapter.MainViewHolder>() {
    var data = mutableListOf<MyPageCherryLevelDataclass>()

    class MainViewHolder(private val binding: MyPageCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(testData: MyPageCherryLevelDataclass) {
            binding.mypageCherryName.text = testData.name
            binding.mypageCherryLevel.text = testData.level
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