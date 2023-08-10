package com.example.sunscreen.extensions

import android.annotation.SuppressLint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale


@SuppressLint("SimpleDateFormat")
fun Instant.toStringDate(): String {
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault() )
        .withZone(ZoneId.systemDefault())
    return formatter.format(this)
}
