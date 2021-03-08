package com.sopt.cherish

import android.app.Application
import com.sopt.cherish.di.Injection
import com.sopt.cherish.local.EncryptedSharedPreferenceController
import com.sopt.cherish.util.PixelRatio

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeSingletons()
        initializeSharedPreference()
    }

    private fun initializeSingletons() {
        pixelRatio = PixelRatio(this)
    }

    private fun initializeSharedPreference() {
        sharedPreferenceController =
            EncryptedSharedPreferenceController(Injection.provideEncryptedSharedPreference(this))
    }

    companion object {
        lateinit var pixelRatio: PixelRatio
        lateinit var sharedPreferenceController: EncryptedSharedPreferenceController
    }
}