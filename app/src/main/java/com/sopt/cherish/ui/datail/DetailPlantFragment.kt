package com.sopt.cherish.ui.datail

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentDetailPlantBinding
import com.sopt.cherish.ui.adapter.DetailMemoAdapter
import com.sopt.cherish.ui.datail.calendar.CalendarFragment
import com.sopt.cherish.ui.domain.MemoListDataclass


class DetailPlantFragment : Fragment() {

    private lateinit var circleProgressbar: CircleProgressbar
    private val viewModel: DetailPlantViewModel by activityViewModels()

    var memoList = arrayListOf<MemoListDataclass>(
        MemoListDataclass("12/2", "다음주에 대머리쉬 출근"),
        MemoListDataclass("12/28", "내일 체리쉬 사퇴")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailPlantBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_plant, container, false)

        // 유저 원형 프로그레스바 보여주는 부분
        circleProgressbar = binding.test
        val animationDuration = 100
        circleProgressbar.setProgressWithAnimation(45.0f, animationDuration)


        // memolist 어댑터 연결 부분
        val mAdapter = DetailMemoAdapter(memoList)
        binding.recyclerDetail.adapter = mAdapter

        binding.recyclerDetail.addItemDecoration(VerticalSpaceItemDecoration(20))

        binding.recyclerDetail.layoutManager = LinearLayoutManager(context)
        binding.recyclerDetail.setHasFixedSize(true)

        return binding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.finish()
                return true
            }

        }
        when (item.itemId) {
            R.id.calendar -> {
                val transaction = childFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_detail, CalendarFragment())
                transaction.commit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}