package com.sopt.cherish.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sopt.cherish.R
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.tutorial.TutorialActivity
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.extension.shortToast

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()

        requestCherishPermissions()
        WateringDialogFragment().show(supportFragmentManager, "MainActivity")
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this@MainActivity, Injection.provideMainViewModelFactory()).get(
                MainViewModel::class.java
        )
    }

    private fun requestCherishPermissions() {
        PermissionUtil.requestCherishPermission(this, object : PermissionUtil.PermissionListener {
            override fun onPermissionGranted() {
                /*startTutorialActivityAndFinish()*/
            }

            override fun onPermissionShouldBeGranted(deniedPermissions: List<String>) {
                shortToast(this@MainActivity, "권한 허용이 안되어있습니다. $deniedPermissions")
                openSettings()
            }

            override fun onAnyPermissionPermanentlyDenied(deniedPermissions: List<String>, permanentDeniedPermissions: List<String>) {
                shortToast(this@MainActivity, "권한 허용이 영구적으로 거부되었습니다. $permanentDeniedPermissions")
                openSettings()
            }
        })
    }

    private fun openSettings() {
        PermissionUtil.openPermissionSettings(this)
    }

    private fun startTutorialActivityAndFinish() {
        startActivity(Intent(this, TutorialActivity::class.java))
        finish()
    }
}