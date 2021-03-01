package com.sopt.cherish.ui.main.manageplant

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

    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageplantBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setFragment(ManagePlantFragmentSearch())


    }

    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.manage_frame, fragment.apply {
            arguments = Bundle().apply {
                putInt("searchmanageid", intent.getIntExtra("searchuserid", 0))
                Log.d("Enrollmentphoneactiviy", count.toString())

            }
        })
        transaction.commit()
    }

    fun replaceFragment(index: Int, data: List<MyPageCherishData>?, isSearched: Boolean) {
        // search = isSearched
        val transAction = supportFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                if (isSearched) //검색창 있는 뷰
                    transAction.replace(R.id.my_page_bottom_container, PlantSearchFragment(data))
                        .commit()
                else //검색창 없는 뷰
                    transAction.replace(R.id.my_page_bottom_container, PlantFragment(data)).commit()
            }
            1 -> {
                if (PermissionUtil.isCheckedReadContactsPermission(this)) {
                    if (isSearched)
                        transAction.replace(
                            R.id.my_page_bottom_container, MyPagePhoneBookSearchFragment()
                        ).commit()
                    else
                        transAction.replace(
                            R.id.my_page_bottom_container, MyPagePhoneBookFragment()
                        ).commit()
                } else {
                    PermissionUtil.openPermissionSettings(this)
                }
                //true
            }
        }
    }


}