package com.sopt.cherish.ui.datail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R

/**
 * 식물 상세 입력 , 식물 상세 재입력의 경우도 이 뷰 사용하면 됨
 */
class DetailUserInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user_input)
    }
}