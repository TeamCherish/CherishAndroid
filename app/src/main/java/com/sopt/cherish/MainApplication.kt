package com.sopt.cherish

import android.app.Application
import com.sopt.cherish.util.PixelRatio

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeSingletons()
    }

    private fun initializeSingletons() {
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var pixelRatio: PixelRatio
    }
}