package com.arvind.moviesapp.utils

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*
import kotlin.time.Duration.Companion.minutes

fun Int.hourMinutes(): String {
    return "${this.minutes.inWholeHours} hrs ${this % 60} mins"
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

@SuppressLint("SimpleDateFormat")
fun Date.convertDate(dateString: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd")
    format.isLenient = false;
    val dateObj = format.parse(dateString)
    val expectedFormat = SimpleDateFormat("MMM dd, yyyy")

    val outFormat = expectedFormat.format(dateObj)
    return outFormat.format(date)
}

fun currencyFormatter(num: String): String? {
    val m = num.toDouble()
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(m)
}

fun Double.toFormatAmount(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("INR")
    return format.format(this.roundToInt())
}

fun getFormatedNumber(count: Long): String {
    val array = arrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val value = floor(log10(count.toDouble())).toInt()
    val base = value / 3
    if (value >= 3 && base < array.size) {
        return DecimalFormat("#0.0").format(count / 10.0.pow((base * 3).toDouble())) + array[base]
    } else {
        return DecimalFormat("#,##0").format(count)
    }
}



