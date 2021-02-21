package com.sopt.cherish.ui.enrollment

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityEnrollmentPhoneBinding
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.WeekPickerDialogFragment

/**
 * 연락처 동기화
 */

class EnrollmentPhoneActivity : AppCompatActivity(),
    WeekPickerDialogFragment.TestDialogFragmentListener,
    ClockPickerDialogFragment.TestDialogFragmentListener {


    private lateinit var binding: ActivityEnrollmentPhoneBinding

    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEnrollmentPhoneBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // val userid = intent.getIntExtra("userId")

        //setToolbar()
        //phoneBookFragment=findViewById(R.layout.fragment_phone_book)
        count = intent.getIntExtra("userId", 0)
        setFragment(PhoneBookFragment())
    }

    private fun setToolbar() {


        // 툴바 왼쪽 버튼 설정
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽 버튼 사용 여부 true
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_gnb_back)  // 왼쪽 버튼 이미지 설정
        supportActionBar!!.setDisplayShowTitleEnabled(true)    // 타이틀 안보이게 하기
        supportActionBar!!.title = "식물 선택"
    }

    fun setActionBarTitle(title: String?) {
        setSupportActionBar(binding.toolbarEnrollPhone)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.icon_gnb_back)
            actionBar.setDisplayShowTitleEnabled(false)

            //actionBar.setTitle(title)
            binding.toolbarTitle.text = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_enroll, fragment.apply {
            arguments = Bundle().apply {
                putInt("useridenroll", count)
                Log.d("Enrollmentphoneactiviy", count.toString())

            }
        })
        transaction.commit()
    }

    override fun onTestDialogweek(dialog: DialogFragment?, someData: String?) {
        Log.d("nana", someData.toString())
        var textweek: TextView = findViewById(R.id.water_alarm_week)
        textweek.text = someData.toString()

    }

    override fun onTestDialogClock(dialog: DialogFragment?, someData: String?) {
        Log.d("nana", someData.toString())
        var textclock: TextView = findViewById(R.id.water_alarm_time)

        textclock.text = someData.toString()
    }


}