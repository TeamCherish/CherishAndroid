package com.sopt.cherish.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val simpleDateFormatKorean = SimpleDateFormat("yyyy년 mm월 dd일", Locale.KOREA)
    private val simpleDateFormatBar = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    fun convertDateToString(date: Date): String = simpleDateFormatBar.format(date)

    fun convertStringToDate(stringDate: String): Date? = simpleDateFormatKorean.parse(stringDate)

    // CalendarDay로 변환되는 녀석이 하나 필요

// 그 CalendarDay들을 뭐라해야하지 list? 로 두는 거죠

}