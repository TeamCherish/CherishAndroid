package com.sopt.cherish.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R

import com.sopt.cherish.databinding.ActivityMainBinding
import com.sopt.cherish.ui.main.home.HomeFragment
import com.sopt.cherish.ui.main.manageplant.ManagePlantFragment
import com.sopt.cherish.ui.main.setting.SettingFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val homeFragment=HomeFragment()
        val managePlantFragment=ManagePlantFragment()
        val settingFragment=SettingFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container,homeFragment).commit()


        binding.mainBottomNavi.setOnNavigationItemSelectedListener{
            val transAction=supportFragmentManager.beginTransaction()

            when(it.itemId){
                R.id.main_home-> {
                    transAction.replace(R.id.main_fragment_container, homeFragment).commit()
                    true
                }
                R.id.main_manage_plant->{
                    transAction.replace(R.id.main_fragment_container, managePlantFragment).commit()
                    true
                }
                R.id.main_setting->{
                    transAction.replace(R.id.main_fragment_container, settingFragment)
                    true
                }else->{
                throw AssertionError()
            }
            }
        }
    }

}

