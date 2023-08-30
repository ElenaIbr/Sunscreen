package com.example.domain.services

import android.content.Context

interface NotificationsManager {
    fun startNotifications(
        reminderTime: String
    )
    fun stopNotifications()
}
