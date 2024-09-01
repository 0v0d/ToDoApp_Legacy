package com.example.todoapp.utility

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtility {
    fun getFormattedDate(date: Date): String =
        SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPANESE).format(date)

    fun parseDate(dueDate: String): Date? = dueDate.ifEmpty { null }?.let {
        SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPANESE).parse(dueDate)
    }
}