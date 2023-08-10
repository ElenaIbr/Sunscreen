package com.example.sunscreen.extensions

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Instant.toStringDate(): String {
    val myDate: Date = Date.from(this)
    val formatter = SimpleDateFormat("dd/mm/yyyy")
    return formatter.format(myDate)
}
