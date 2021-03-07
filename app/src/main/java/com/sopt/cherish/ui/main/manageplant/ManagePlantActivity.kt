package com.sopt.cherish.ui.main.manageplant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityManageplantBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.ui.enrollment.EnrollmentViewModel
import com.sopt.cherish.util.PermissionUtil

class ManagePlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageplantBinding
    private val viewModel: EnrollmentViewModel by viewModels { Injection.provideEnrollmentViewModelFactory() }
    private var tabIndex:Int=0
    var data: ArrayList<MyPageCherishData>?=null
    var plantCount:String?=""
    var phoneCount:String?=""
    private var userId:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageplantBinding.inflate(layoutInflater)

        val intent= getIntent()
        tabIndex=intent.getIntExtra("tabIndex",0)
        plantCount=intent.getStringExtra("plantCount")
        phoneCount=intent.getStringExtra("phoneCount")
        data=intent.getSerializableExtra("plantData") as ArrayList<MyPageCherishData>?

        Log.d("getTabIndex",tabIndex.toString())
        Log.d("getPlantData",data.toString())
        Log.d("getPlantCount",plantCount.toString())
        Log.d("getPhoneCount",phoneCount.toString())
        setFrameLayout()

        binding.cancelBtn.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }


    private fun setFrameLayout(){
        val transAction = supportFragmentManager.beginTransaction()
        when (tabIndex) {
            0 -> {
                transAction.replace(R.id.frame_container, PlantSearchFragment(data)).commit()
            }
            1 -> {
                if (PermissionUtil.isCheckedReadContactsPermission(this)) {
                    transAction.replace(
                        R.id.frame_container, MyPagePhoneBookSearchFragment()
                    ).commit()
                } else {
                    PermissionUtil.openPermissionSettings(this)
                }
                //true
            }
        }
    }
}