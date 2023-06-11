package com.example.domain.models

import java.time.DayOfWeek

data class Notification (
    val notificationEnabled: Boolean = false,
    val notificationHour: Int = 8,
    val notificationMinute: Int = 0,
    val days: List<DayOfWeek> = emptyList()
)
