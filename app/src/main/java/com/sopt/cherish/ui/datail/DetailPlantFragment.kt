package com.sopt.cherish.ui.datail

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityDetailPlantBinding
import com.sopt.cherish.databinding.FragmentDetailPlantBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.model.MemoListDataclass
import com.sopt.cherish.ui.adapter.DetailMemoAdapter


class DetailPlantFragment : Fragment() {

    private lateinit var circleProgressbar: CircleProgressbar
    private lateinit var binding: FragmentDetailPlantBinding
    private lateinit var viewModel: DetailPlantViewModel

    var memoList = arrayListOf<MemoListDataclass>(
        MemoListDataclass("12/2", "다음주에 대머리쉬 출근"),
        MemoListDataclass("12/28", "내일 체리쉬 사퇴")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_detail_plant, container, false)

        initializeViewModel()



        binding = FragmentDetailPlantBinding.bind(view)


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


        return view
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

    private fun initializeViewModel() {
        viewModel =
            ViewModelProvider(
                this@DetailPlantFragment,
                Injection.provideDetailViewModelFactory()
            ).get(
                DetailPlantViewModel::class.java
            )
    }

}