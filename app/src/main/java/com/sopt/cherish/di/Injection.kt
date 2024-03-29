package com.sopt.cherish.di

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.repository.*
import com.sopt.cherish.ui.factory.*
import com.sopt.cherish.util.MyKeyStore
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.util.concurrent.TimeUnit

/**
 * Created on 01-03 by SSong-develop
 * do not use koin or hilt , just use singleton pattern
 * we need to divide file in module like DetailModule
 */
object Injection {
    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory(
            provideMainRepository(), provideWateringRepository(), provideCalendarRepository(),
            provideNotificationRepository(), provideReviewRepository()
        )
    }

    fun provideHomeBlankViewModelFactory(): ViewModelProvider.Factory {
        return HomeBlankViewModelFactory()
    }

    // User di
    private fun provideUserAPI(): UserAPI {
        return RetrofitBuilder.userAPI
    }

    private fun provideMainRepository(): MainRepository {
        return MainRepository(
            provideUserAPI()
        )
    }

    // Detail di
    private fun provideDetailPlantRepository(): DetailPlantRepository {
        return DetailPlantRepository(provideCalendarAPI(), provideReviewAPI())
    }

    fun provideDetailViewModelFactory(): ViewModelProvider.Factory {
        return DetailViewModelFactory(provideDetailPlantRepository(), provideWateringRepository())
    }

    // watering di
    private fun provideWateringRepository(): WateringRepository {
        return WateringRepository(provideWateringAPI())
    }

    private fun provideWateringAPI(): WateringAPI {
        return RetrofitBuilder.wateringAPI
    }

    // review di
    private fun provideReviewRepository(): ReviewRepository {
        return ReviewRepository(provideReviewAPI())
    }

    private fun provideReviewAPI(): ReviewAPI {
        return RetrofitBuilder.reviewAPI
    }

    // calendar di
    private fun provideCalendarRepository(): CalendarRepository {
        return CalendarRepository(provideCalendarAPI())
    }

    private fun provideCalendarAPI(): CalendarAPI {
        return RetrofitBuilder.calendarAPI
    }

    // enrollment di
    fun provideEnrollmentViewModelFactory(): ViewModelProvider.Factory {
        return EnrollmentViewModelFactory()
    }

    // review di
    fun provideReviewViewModelFactory(): ViewModelProvider.Factory {
        return ReviewViewModelFactory(provideReviewRepository(), provideNotificationRepository())
    }

    // notification di
    private fun provideNotificationAPI(): NotificationAPI {
        return RetrofitBuilder.notificationAPI
    }

    private fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepository(provideNotificationAPI())
    }

    // NotificationManager
    fun provideNotificationManager(context: Context): NotificationManager =
        ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

    // 암호화 쉐어드 프리퍼런스 di , token 발급되었을 때 여기다가 넣어놓고서 작업하면 될거 같음
    fun provideEncryptedSharedPreference(context: Context): SharedPreferences {
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,
            MyKeyStore.provideEncryptedSharedPrefsName(),
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            .build()

}