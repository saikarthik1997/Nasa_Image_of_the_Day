package com.sri.nasaimageoftheday.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatDate(inputDateStr: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(inputDateStr)
            val outputFormat = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return inputDateStr // Return the original string if parsing fails
        }
    }
}
