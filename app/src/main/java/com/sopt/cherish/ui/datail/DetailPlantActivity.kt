package com.sopt.cherish.ui.datail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.AcitivityEnrollplantBinding
import com.sopt.cherish.databinding.ActivityDetailPlantBinding
import com.sopt.cherish.remote.model.MemoListDataclass
import com.sopt.cherish.ui.adapter.DetailMemoAdapter



/**
 * 식물 상세보기
 */

//created by nayoung : 사용자가 메모한 내용 보여주는 activity
class DetailPlantActivity : AppCompatActivity() {

    private lateinit var circleProgressbar: CircleProgressbar
    private lateinit var binding:ActivityDetailPlantBinding

    var memoList = arrayListOf<MemoListDataclass>(
            MemoListDataclass("12/2", "다음주에 대머리쉬 출근"),
            MemoListDataclass("12/28", "내일 대머리쉬 사퇴")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDetailPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 유저 원형 프로그레스바 보여주는 부분
        circleProgressbar = findViewById(R.id.test)
        val animationDuration = 100
        circleProgressbar.setProgressWithAnimation(45.0f, animationDuration)


        // memolist 어댑터 연결 부분
        val mAdapter = DetailMemoAdapter(memoList)
        binding.recyclerDetail.adapter = mAdapter

        binding.recyclerDetail.layoutManager = LinearLayoutManager(this)
        binding.recyclerDetail.setHasFixedSize(true)
    }
}