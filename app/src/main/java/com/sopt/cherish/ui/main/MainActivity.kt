package com.sopt.cherish.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.ui.adapter.MainBottomNaviAdapter
import com.sopt.cherish.ui.main.home.HomeFragment
import com.sopt.cherish.ui.main.manageplant.ManagePlantFragment
import com.sopt.cherish.ui.main.setting.SettingFragment
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewpagerAdapter: MainBottomNaviAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        viewpagerAdapter=MainBottomNaviAdapter(supportFragmentManager)
        viewpagerAdapter.fragments=listOf(
                HomeFragment(),
                ManagePlantFragment(),
                SettingFragment()
        )

        binding.bottomViewpager.adapter=viewpagerAdapter

        binding.mainBottomNavi.setOnNavigationItemSelectedListener{
            var index:Int by Delegates.notNull<Int>()
            when(it.itemId){
                R.id.main_home->index=0
                R.id.main_person->index=1
                R.id.main_more->index=2
            }
            binding.bottomViewpager.currentItem=index
            true
        }
    }
}
