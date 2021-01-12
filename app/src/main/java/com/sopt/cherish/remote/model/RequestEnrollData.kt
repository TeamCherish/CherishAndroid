package com.sopt.cherish.remote.model

import android.provider.ContactsContract

data class RequestEnrollData(
    val name : String,
    val nickname : String,
    val birth:String,
    val phone:String,
    val cycle_date:Int,
    val notice_time:String,
    val UserId: Int

)
