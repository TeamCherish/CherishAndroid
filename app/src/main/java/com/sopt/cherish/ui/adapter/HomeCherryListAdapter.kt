package com.sopt.cherish.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.MainCherryItemBinding
import com.sopt.cherish.remote.api.User

class HomeCherryListAdapter : RecyclerView.Adapter<HomeCherryListAdapter.MainViewHolder>() {
    var data = mutableListOf<User>()

    class MainViewHolder(private val binding: MainCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: User) {
            binding.homeUserData = userData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MainCherryItemBinding =
            MainCherryItemBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}