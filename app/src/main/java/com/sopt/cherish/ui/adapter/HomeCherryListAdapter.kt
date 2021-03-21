package com.sopt.cherish.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.databinding.MainCherryItemBinding
import com.sopt.cherish.remote.api.User

class HomeCherryListAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HomeCherryListAdapter.MainViewHolder>() {

    var data = mutableListOf<User>()
    var lastSelectedPosition = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MainCherryItemBinding =
            MainCherryItemBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (position == 0) {
            holder.bind(data[position], itemClickListener)
        }
        holder.bind(data[position], itemClickListener)
    }

    override fun getItemCount(): Int = data.size

    inner class MainViewHolder(private val binding: MainCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: User, clickListener: OnItemClickListener) {
            if (adapterPosition == 0) {
                binding.homeItemFirstSelector.visibility = View.VISIBLE
            } else {
                binding.homeItemFirstSelector.visibility = View.INVISIBLE
            }
            setAlphaItemClicked(binding, adapterPosition)
            binding.apply {
                homeUserData = userData
                executePendingBindings()
            }
            binding.root.apply {
                setOnClickListener {
                    // 함수로 만들어야 하는데 이름이 마땅하게 안떠오름....ㅠ
                    if (lastSelectedPosition != adapterPosition) {
                        notifyItemRangeChanged(0, data.size)
                        lastSelectedPosition = adapterPosition
                    }
                    clickListener.onItemClick(binding, adapterPosition)
                }
            }
        }
    }

    private fun setAlphaItemClicked(binding: MainCherryItemBinding, itemPosition: Int) {
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
    }
}

interface OnItemClickListener {
    fun onItemClick(itemBinding: MainCherryItemBinding, position: Int)
}