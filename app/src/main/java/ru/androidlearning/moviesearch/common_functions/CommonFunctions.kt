package ru.androidlearning.moviesearch.common_functions

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy/MM/dd"
fun getDateFromString(dateStr: String): Date? = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(dateStr)
fun getStringFromDate(date: Date): String = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)
