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

    private fun moveItemToFront() {
        // 클릭 된 녀석은 alpha값이 변해진 상태 그대로 있고
        // 리사이클러뷰 가장 첫번째에 동시에 보이게 된다.
        // 만약 , 2번 아이템을 클릭하면 2번자리의 아이템이 alpha값이 줄어 희미하게 보이게 되고
        // 리사이클러뷰 첫번째에서 선명하게 주변에 선이 쳐진 상태로 클릭이 되어있는 상태라는 것을 보여주게 된다.
        // 그럼 어떻게 작업을 해야할까? 훈기야 생각해보자
        // 1. 리사이클러뷰의 0번째 아이템은 비워둔 상태로 시작한다. 즉, blank로 만들어 준다.
        // 1-1. 혹은 리스트의 맨 앞의 아이템을 복사해서 앞에다가 붙여놓는다.
        // 사실상 1-1 방법이 좀 더 맞는거 같음.
        // 방법을 생각해볼까? 훈기야???
    }
}

interface OnItemClickListener {
    fun onItemClick(itemBinding: MainCherryItemBinding, position: Int)
}