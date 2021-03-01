package com.sopt.cherish.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.MyPageCherryItemBinding
import com.sopt.cherish.remote.api.MyPageCherishData

class MyPageBottomSheetAdapter(private var data: List<MyPageCherishData>?) :
    RecyclerView.Adapter<MyPageBottomSheetAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: MyPageCherryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(cherishData: MyPageCherishData) {
            binding.mypageCherryNickname.text = cherishData.nickName
            binding.mypageCherryName.text = cherishData.name
            binding.mypageCherryLevel.text = "Lv. " + cherishData.level.toString()

            //빨간색: D+
            if (cherishData.dDay < 0) {
                //Log.d("dday ", cherishData.dDay.toString())
                //Log.d("nickname", cherishData.nickName)
                val dDay=cherishData.dDay*(-1) //양수로 바꿈
                Log.d("change dDay",dDay.toString())
                binding.myPageDDay.text = "D+" + dDay.toString()
                binding.myPageDDay.setBackgroundResource(R.drawable.my_page_chip_red)
                binding.myPageDDay.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_pink_sub
                    )
                )
            } else if (cherishData.dDay == 0) {
                binding.myPageDDay.text = "D-day"
                binding.myPageDDay.setBackgroundResource(R.drawable.my_page_chip_red) //초록색으로
                binding.myPageDDay.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_pink_sub
                    )
                )
            } else { //양수: 초록색: D-
                binding.myPageDDay.text = "D-" + cherishData.dDay.toString()
                binding.myPageDDay.setBackgroundResource(R.drawable.my_page_chip_green) //초록색으로
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MyPageCherryItemBinding =
            MyPageCherryItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data!![position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = data!!.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}