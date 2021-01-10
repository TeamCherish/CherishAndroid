package com.sopt.cherish.ui.datail

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityDetailPlantBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.datail.calendar.CalendarFragment

import com.sopt.cherish.ui.domain.MemoListDataclass



/**
 * 식물 상세보기
 */

//created by nayoung : 사용자가 메모한 내용 보여주는 activity
class DetailPlantActivity : AppCompatActivity() {

    private lateinit var circleProgressbar: CircleProgressbar
    private lateinit var binding: ActivityDetailPlantBinding

    var memoList = arrayListOf<MemoListDataclass>(
        MemoListDataclass("12/2", "다음주에 대머리쉬 출근"),
        MemoListDataclass("12/28", "내일 체리쉬 사퇴")
    )

    private lateinit var viewModel: DetailPlantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // initializeViewModel()
        binding = ActivityDetailPlantBinding.inflate(layoutInflater)
        setFragment(DetailPlantFragment())
        setActionBarTitle("식물 상세")
        setContentView(binding.root)

        /* binding = ActivityDetailPlantBinding.inflate(layoutInflater)
         setContentView(binding.root)
         // 유저 원형 프로그레스바 보여주는 부분
         circleProgressbar = findViewById(R.id.test)
         val animationDuration = 100
         circleProgressbar.setProgressWithAnimation(45.0f, animationDuration)
 */

/*
        // memolist 어댑터 연결 부분
        val mAdapter = DetailMemoAdapter(memoList)
        binding.recyclerDetail.adapter = mAdapter
        binding.recyclerDetail.addItemDecoration(VerticalSpaceItemDecoration(20))
        binding.recyclerDetail.layoutManager = LinearLayoutManager(this)
        binding.recyclerDetail.setHasFixedSize(true)*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
            R.id.calendar -> {
                setFragment(CalendarFragment())
                return true
            }

            R.id.setting -> {
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun setFragment(fragment: Fragment){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_detail, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun setActionBarTitle(title: String?) {
        setSupportActionBar(binding.toolbarDetail)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.icon_gnb_back)
            actionBar.setDisplayShowTitleEnabled(false)

            //actionBar.setTitle(title)
            binding.toolbarDetailTitle.text=title
        }
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
                this@DetailPlantActivity,
                Injection.provideDetailViewModelFactory()
            ).get(
                DetailPlantViewModel::class.java
            )
    }
}