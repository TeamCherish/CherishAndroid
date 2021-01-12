package com.sopt.cherish.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val simpleDateFormatKorean = SimpleDateFormat("yyyy년 mm월 dd일", Locale.KOREA)
    private val simpleDateFormatBar = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    private fun convertDateToString(date: Date): String = simpleDateFormatBar.format(date)

    fun convertStringToDate(stringDate: String): Date? = simpleDateFormatKorean.parse(stringDate)

    // CalendarDay로 변환되는 녀석이 하나 필요
    private fun getYear(stringDate: String) = Integer.parseInt(stringDate.substring(0, 4))

    private fun getMonth(stringDate: String) = Integer.parseInt(stringDate.substring(5, 7))

    private fun getDay(stringDate: String) = Integer.parseInt(stringDate.substring(8, 10))


    fun convertDateToCalendarDay(date: Date): CalendarDay {
        val stringDate = convertDateToString(date)
        return CalendarDay.from(getYear(stringDate), getMonth(stringDate), getDay(stringDate))
    }
}