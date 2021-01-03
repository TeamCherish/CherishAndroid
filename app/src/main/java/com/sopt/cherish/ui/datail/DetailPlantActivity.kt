package com.sopt.cherish.ui.datail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.remote.model.MemoListDataclass
import com.sopt.cherish.ui.adapter.DetailMemoAdapter
import kotlinx.android.synthetic.main.activity_detailplant.*

/**
 * 식물 상세보기
 */
class DetailPlantActivity : AppCompatActivity() {

    private lateinit var circleProgressbar: CircleProgressbar

    var memoList= arrayListOf<MemoListDataclass>(
            MemoListDataclass("12/2","다음주에 대머리쉬 출근"),
            MemoListDataclass("12/28","내일 대머리쉬 사퇴")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailplant)

        circleProgressbar = findViewById(R.id.test)

        val animationDuration = 100
        circleProgressbar.setProgressWithAnimation(45.0f,animationDuration)

        val mAdapter =DetailMemoAdapter(memoList)
        recycler_detail.adapter=mAdapter

        recycler_detail.layoutManager=LinearLayoutManager(this)
        recycler_detail.setHasFixedSize(true)
    }
}