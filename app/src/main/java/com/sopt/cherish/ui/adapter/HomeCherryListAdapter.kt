package com.sopt.cherish.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.MainCherryItemBinding
import com.sopt.cherish.remote.api.User

class HomeCherryListAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HomeCherryListAdapter.MainViewHolder>() {

    var data = mutableListOf<User>()
    private var lastSelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MainCherryItemBinding =
            MainCherryItemBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position], itemClickListener)
    }

    override fun getItemCount(): Int = data.size

    inner class MainViewHolder(private val binding: MainCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: User, clickListener: OnItemClickListener) {
            val itemPosition = adapterPosition
            // 이거 함수화 하고 알고리즘 좀 더 세련되게 하면 괜찮을거 같음
            if (lastSelectedPosition == itemPosition) {
                binding.apply {
                    mainUserImg.alpha = 0.2f
                    mainUserWater.alpha = 0.2f
                    mainUserName.alpha = 0.2f
                }
            } else {
                binding.apply {
                    mainUserImg.alpha = 1.0f
                    mainUserWater.alpha = 1.0f
                    mainUserName.alpha = 1.0f
                }
            }
            binding.apply {
                homeUserData = userData
                executePendingBindings()
            }
            binding.root.apply {
                setOnClickListener {
                    if (lastSelectedPosition != itemPosition) {
                        notifyItemRangeChanged(0, data.size)
                        lastSelectedPosition = itemPosition
                    }
                    clickListener.onItemClick(binding, itemPosition)
                }
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(itemBinding: MainCherryItemBinding, position: Int)
}