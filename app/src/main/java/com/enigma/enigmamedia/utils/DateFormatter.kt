package com.enigma.enigmamedia.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

class DateFormatter {

    @SuppressLint("SimpleDateFormat")
    fun formatter(date: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("dd MMMM yyyy")

        return try {
            val dateFormat = inputFormat.parse(date)
            val formattedDate = dateFormat?.let { outputFormat.format(it) }
            formattedDate ?: ""
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }


    }

}