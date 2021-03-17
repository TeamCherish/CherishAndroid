package com.sopt.cherish.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityDetailPlantBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.detail.calendar.CalendarFragment
import com.sopt.cherish.ui.dialog.BirthPickerDialogFragment
import com.sopt.cherish.ui.dialog.ClockPickerDialogFragment
import com.sopt.cherish.ui.dialog.WeekPickerDialogFragment
import com.sopt.cherish.ui.enrollment.EnrollModifyPlantFragment


/**
 * 식물 상세보기
 */

//created by nayoung : 사용자가 메모한 내용 보여주는 activity
class DetailPlantActivity : AppCompatActivity(),
    WeekPickerDialogFragment.TestDialogFragmentListener,
    ClockPickerDialogFragment.TestDialogFragmentListener,
    BirthPickerDialogFragment.TestDialogFragmentListener{

    private lateinit var circleProgressbar: CircleProgressbar
    private lateinit var binding: ActivityDetailPlantBinding
    private val viewModel: DetailPlantViewModel by viewModels { Injection.provideDetailViewModelFactory() }

    var cherishid_main = 0
    var cherishid_plant = 0
    var plantId = 1

    private lateinit var cherishPhoneNumber: String
    private lateinit var cherishNickname: String
    private lateinit var userNickname: String

    var cherishuserId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPlantBinding.inflate(layoutInflater)


        Log.d("plantId", intent.getIntExtra("plantId", 100).toString())
        //식물 아이디
        plantId = intent.getIntExtra("plantId", 0)

        //체리쉬아이디 -메인에서오는지 마이페이지에서 오는지 분기처리해줌
        if (intent.getIntExtra("cherishId", 0) == 0) {
            cherishid_main = intent.getIntExtra("Id", 0)
            viewModel.cherishId.value = cherishid_main
        }
        if (intent.getIntExtra("Id", 0) == 0) {
            cherishid_main = intent.getIntExtra("cherishId", 0)
            viewModel.cherishId.value = cherishid_main
        }

        //유저 폰넘버
        cherishPhoneNumber = intent.getStringExtra("cherishUserPhoneNumber").toString()
        viewModel.cherishPhoneNumber.value = cherishPhoneNumber
        //식물 애칭
        cherishNickname = intent.getStringExtra("cherishNickname").toString()
        viewModel.cherishNickname.value = cherishNickname
        //유저 이름
        userNickname = intent.getStringExtra("userNickname").toString()
        viewModel.userNickname.value = userNickname
        //유저 아이디
        cherishuserId = intent.getIntExtra("userId", 0)
        viewModel.userId.value = cherishuserId
        //유저 dDay
        viewModel.selectedUserDday = intent.getIntExtra("selectedUserDday", 0)

        Log.d("cherishid_main", cherishid_main.toString())
        Log.d("cherishid_plant", cherishid_plant.toString())

        viewModel.fetchCalendarData()
        setFragment(DetailPlantFragment())
        setActionBarTitle("식물 상세")
        setContentView(binding.root)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            menu.getItem(2).isVisible = false
        } // invisible menuitem 2

        invalidateOptionsMenu()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

        }
        when (item.itemId) {
            R.id.calendar -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_detail, CalendarFragment())
                // if (transaction == null) {
                transaction.addToBackStack(null)
                // }
                transaction.commit()

                return true
            }
            R.id.setting -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fragment_detail,
                    EnrollModifyPlantFragment().apply {
                        arguments = Bundle().apply {
                            //수정과 삭제는 체리쉬 아이디만 필요함
                            putInt("cherishid_modify", cherishid_main)

                        }
                    })
                // if (transaction == null) {
                transaction.addToBackStack(null)
                // }
                transaction.commit()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_detail, fragment.apply {
            arguments = Bundle().apply {
                putInt("plantId_detail", plantId)

                putInt("cherishidmain_detail", cherishid_main)
                putString("cherishUserPhoneNumber_detail", cherishPhoneNumber)
                putString("cherishNickname_detail", cherishNickname)
                putString("userNickname_detail", userNickname)
                putInt("userId_detail", cherishuserId)


                //Log.d("nanana", cherishid.toString())
            }
        })
        transaction.commit()
    }

    fun OptionsMenu(menu: Menu): Boolean {
        menu.getItem(0).isVisible = false //disable menuitem 5
        menu.getItem(1).isVisible = false // invisible menuitem 2
        invalidateOptionsMenu()
        return true
    }


    fun setActionBarTitle(title: String?) {
        setSupportActionBar(binding.toolbarDetail)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.icon_gnb_back)
            actionBar.setDisplayShowTitleEnabled(false)

            //actionBar.setTitle(title)
            binding.toolbarDetailTitle.text = title
        }
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



    override fun onTestDialogBirth(dialog: DialogFragment?, someData: String?) {


        var textbirth: TextView = findViewById(R.id.edit_birth)

        textbirth.text = someData.toString()
    }

}