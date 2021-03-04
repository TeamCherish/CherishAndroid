@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.sopt.cherish.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val simpleDateFormatKorean = SimpleDateFormat("yyyy년 mm월 dd일", Locale.KOREA)
    private val simpleDateFormatBar = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    fun convertStringToDate(stringDate: String): Date? =
        simpleDateFormatKorean.parse(stringDate)

    fun convertStringToDateBar(stringDate: String?): Date? {
        return if (stringDate != "_ _") {
            simpleDateFormatBar.parse(stringDate)
        } else {
            null
        }
    }


    fun getYear(stringDate: String) = Integer.parseInt(stringDate.substring(0, 4))

    fun getMonth(stringDate: String) = Integer.parseInt(stringDate.substring(5, 7))

    fun getDay(stringDate: String) = Integer.parseInt(stringDate.substring(8, 10))


    fun convertDateToCalendarDay(date: Date): CalendarDay {
        val stringDate = convertDateToString(date)
        return CalendarDay.from(getYear(stringDate), getMonth(stringDate), getDay(stringDate))
    }

    fun convertDateToString(date: Date): String = simpleDateFormatBar.format(date)

}