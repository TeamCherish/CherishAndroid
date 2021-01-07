package com.sopt.cherish.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sopt.cherish.R
import com.sopt.cherish.ui.domain.MemoListDataclass

//created by nayoung : 식물상세뷰에서 사용자가 남긴 메모를 보여주는 adapter
class DetailMemoAdapter(val memolist: ArrayList<MemoListDataclass>) :
    RecyclerView.Adapter<DetailMemoAdapter.itemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailMemoAdapter.itemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_item_layout, parent, false)

        return itemViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailMemoAdapter.itemViewHolder, position: Int) {
        holder.bind(memolist[position])
    }

    override fun getItemCount(): Int {
        return memolist.size
    }

    inner class itemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userdate = itemView.findViewById<TextView>(R.id.userdate)
        private val usermemo = itemView.findViewById<TextView>(R.id.usermemo)

        fun bind(memolist: MemoListDataclass) {

            userdate.text = memolist.date
            usermemo.text = memolist.memo
        }
    }

}