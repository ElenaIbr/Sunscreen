package com.example.domain.models

data class Notification (
    val notificationEnabled: Boolean = false,
    val start: String = "8:00",
    val end: String = "21:00"
)
