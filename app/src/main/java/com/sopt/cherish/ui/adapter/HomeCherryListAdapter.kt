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
            // 문제는 값이 갱신된 다음이 문제임
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
                    // 방법 1. swap을 어댑터에서 진행하는 경우
                    clickListener.onItemClick(binding, itemPosition)
                }
            }
        }
    }
    // 클릭 된 녀석은 alpha값이 변해진 상태 그대로 있고
    // 리사이클러뷰 가장 첫번째에 동시에 보이게 된다.
    // 만약 , 2번 아이템을 클릭하면 2번자리의 아이템이 alpha값이 줄어 희미하게 보이게 되고
    // 리사이클러뷰 첫번째에서 선명하게 주변에 선이 쳐진 상태로 클릭이 되어있는 상태라는 것을 보여주게 된다.
    // 그럼 어떻게 작업을 해야할까? 훈기야 생각해보자
    // 1. 리사이클러뷰의 0번째 아이템은 비워둔 상태로 시작한다. 즉, blank로 만들어 준다.

}

interface OnItemClickListener {
    fun onItemClick(itemBinding: MainCherryItemBinding, position: Int)
}