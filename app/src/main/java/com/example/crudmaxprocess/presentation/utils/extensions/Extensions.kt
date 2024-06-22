package com.example.crudmaxprocess.presentation.utils.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.toDate(): Date {
    val format = SimpleDateFormat("dd/MM/yyyy")

    try {
        val date = format.parse(this)
        return date
    } catch (e: Exception) {
        println("Erro ao converter a string para Date: ${e.message}")
    }

    return Calendar.getInstance().time
}
fun formatData(data: String): String {
    val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val dataObj = sdf.parse(data)
    val newFormat = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
    return newFormat.format(dataObj)
}