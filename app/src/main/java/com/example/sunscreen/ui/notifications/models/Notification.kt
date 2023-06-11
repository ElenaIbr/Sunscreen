package com.example.sunscreen.ui.notifications.models

import com.example.sunscreen.ui.notifications.components.DayOfWeek

data class Notification (
    val notificationEnabled: Boolean = false,
    val notificationHour: Int,
    val notificationMinute: Int,
    val days: List<DayOfWeek>
)
