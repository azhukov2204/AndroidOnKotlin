package ru.androidlearning.moviesearch.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd  HH:mm:ss"
fun getDateFromString(dateStr: String): Date? = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(dateStr)
fun getStringFromDate(date: Date): String? = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)

fun View.showSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    action: ((View) -> Unit)? = null
) {
    Snackbar.make(this, message, length).setAction(actionText, action).show()
}