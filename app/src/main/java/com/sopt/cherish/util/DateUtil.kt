package com.sopt.cherish.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val simpleDateFormatKorean = SimpleDateFormat("yyyy년 mm월 dd일", Locale.KOREA)
    private val simpleDateFormatBar = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    fun convertDateToString(date: Date): String = simpleDateFormatBar.format(date)

    fun convertStringToDate(stringDate: String): Date? = simpleDateFormatKorean.parse(stringDate)

    fun getYear(date: Date): String {
        return convertDateToString(date).substring(0, 4)
    }

    fun getMonth(date: Date): String {
        return convertDateToString(date).substring(5, 7)
    }

    fun getDay(date: Date): String {
        return convertDateToString(date).substring(8, 10)
    }
}