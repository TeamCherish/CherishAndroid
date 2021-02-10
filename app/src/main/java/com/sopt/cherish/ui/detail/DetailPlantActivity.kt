package com.sopt.cherish.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityDetailPlantBinding
import com.sopt.cherish.ui.detail.calendar.CalendarFragment
import com.sopt.cherish.ui.enrollment.EnrollModifyPlantFragment


/**
 * 식물 상세보기
 */

//created by nayoung : 사용자가 메모한 내용 보여주는 activity
class DetailPlantActivity : AppCompatActivity() {

    private lateinit var circleProgressbar: CircleProgressbar
    private lateinit var binding: ActivityDetailPlantBinding

    var cherishid_main = 0
    var cherishid_plant=0
    var plantId = 1
    var userId=0

    private lateinit var cherishPhoneNumber: String
    private lateinit var cherishNickname: String
    private lateinit var userNickname: String
    var userId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPlantBinding.inflate(layoutInflater)


        Log.d("plantId", intent.getIntExtra("plantId", 100).toString())
        //식물 아이디
        plantId = intent.getIntExtra("plantId", 0)

        //체리쉬아이디 -메인에서오는지 마이페이지에서 오는지 분기처리해줌
        if (intent.getIntExtra("cherishId", 0) == 0) {
            cherishid_main = intent.getIntExtra("Id", 0)
        }
        if (intent.getIntExtra("Id", 0) == 0) {
            cherishid_main = intent.getIntExtra("cherishId", 0)
        }
        //유저 폰넘버
        cherishPhoneNumber = intent.getStringExtra("cherishUserPhoneNumber").toString()
        //식물 애칭
        cherishNickname = intent.getStringExtra("cherishNickname").toString()
        //유저 이름
        userNickname = intent.getStringExtra("userNickname").toString()
        //유저 아이디
        userId = intent.getIntExtra("userId", 0)



        Log.d("cherishid_main", cherishid_main.toString())
        Log.d("cherishid_plant", cherishid_plant.toString())

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
                putInt("userId_detail", userId)


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