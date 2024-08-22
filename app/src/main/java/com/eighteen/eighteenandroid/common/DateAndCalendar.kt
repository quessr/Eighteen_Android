package com.eighteen.eighteenandroid.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val DEFAULT_TIME_FORMAT_STRING = "yyyy-MM-dd'T'hh:mm:ss"
const val HOUR_MINUTE_FORMAT_STRING = "hh:mm"
const val KOREA_YEAR_MONTH_DAY_STRING = "yyyy년 MM월 dd일"

fun Date.toTimeString(timeFormat: String = DEFAULT_TIME_FORMAT_STRING): String = SimpleDateFormat(
    timeFormat,
    Locale.KOREA
).format(this)

fun Date.toCalendar() = Calendar.getInstance().apply { time = this@toCalendar }
