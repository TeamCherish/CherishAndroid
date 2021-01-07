package com.sopt.cherish.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.MainCherryItemBinding
import com.sopt.cherish.remote.model.CherryDataclass

class MainBottomSheetAdapter : RecyclerView.Adapter<MainBottomSheetAdapter.MainViewHolder>() {
    var data = mutableListOf<CherryDataclass>()

    class MainViewHolder(private val binding: MainCherryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(testData: CherryDataclass) {
            binding.mainUserName.text = testData.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MainCherryItemBinding = MainCherryItemBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}