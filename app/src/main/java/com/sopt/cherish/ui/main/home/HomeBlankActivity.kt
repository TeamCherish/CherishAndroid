package com.sopt.cherish.ui.main.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityHomeBlankBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.HomeBlankViewModel
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.extension.shortToast


class HomeBlankActivity : AppCompatActivity() {
    private val viewModel: HomeBlankViewModel by viewModels { Injection.provideHomeBlankViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBlankBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home_blank)
        binding.homeBlankActivity = this
        binding.homeBlankViewModel = viewModel


        viewModel.userId = intent.getIntExtra("userId", -1)
        viewModel.userNickname = intent.getStringExtra("userNickname")!!

        requestCherishPermissions()
    }

    fun navigatePhone() {
        val intent = Intent(this@HomeBlankActivity, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.userId)
        intent.putExtra("userNickname", viewModel.userNickname)
        intent.putExtra("codeFirstStart", CODE_FIRST_START)
        startActivity(intent)
    }

    private fun requestCherishPermissions() {
        PermissionUtil.requestCherishPermission(this, object : PermissionUtil.PermissionListener {
            override fun onPermissionGranted() {

            }

            override fun onPermissionShouldBeGranted(deniedPermissions: List<String>) {
                shortToast(this@HomeBlankActivity, "권한 허용이 안되어있습니다. $deniedPermissions")
                openSettings()
            }

            override fun onAnyPermissionPermanentlyDenied(
                deniedPermissions: List<String>,
                permanentDeniedPermissions: List<String>
            ) {
                shortToast(
                    this@HomeBlankActivity,
                    "권한 허용이 영구적으로 거부되었습니다. $permanentDeniedPermissions"
                )
                openSettings()
            }
        })
    }

    private fun openSettings() {
        PermissionUtil.openPermissionSettings(this)
    }

    companion object {
        private const val CODE_FIRST_START = 1
    }
}