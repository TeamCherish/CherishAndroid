package com.sopt.cherish.ui.datail

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
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
import com.sopt.cherish.ui.enrollment.EnrollModifyPlantFragment


/**
 * 식물 상세보기
 */

//created by nayoung : 사용자가 메모한 내용 보여주는 activity
class DetailPlantActivity : AppCompatActivity() {

    private lateinit var circleProgressbar: CircleProgressbar
    private lateinit var binding: ActivityDetailPlantBinding

    private lateinit var viewModel: DetailPlantViewModel

    var plantid: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPlantBinding.inflate(layoutInflater)
        setFragment(DetailPlantFragment())
        initializeViewModel()


        //Log.d("qwer",intent.getIntExtra("plantId",100).toString())

        setActionBarTitle("식물 상세")
        setContentView(binding.root)
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
                transaction.replace(R.id.fragment_detail, EnrollModifyPlantFragment().apply {
                    arguments = Bundle().apply {

                        putInt("plantidgo_delete", intent.getIntExtra("plantId", 100))

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

                putInt("plantidgo", intent.getIntExtra("plantId", 100))
                Log.d("nanana", intent.getIntExtra("plantId", 100).toString())
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

