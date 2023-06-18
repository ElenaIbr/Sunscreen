package com.example.sunscreen.services

import android.content.Context

interface NotificationManager {
    fun setNotifications(
        context: Context,
        hours: Int,
        minutes: Int,
        reminderId: Int
    )
    fun stopNotifications(
        context: Context,
        reminderId: Int
    )
}
