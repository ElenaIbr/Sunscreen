package com.example.sunscreen.ui.notifications.viewmodel

import com.example.domain.models.Notification
import com.example.domain.models.UserModel

data class NotificationState (
    val user: UserModel? = null,
    val notification: Notification? = null,
    val notificationWasChanged: Boolean = false
)
