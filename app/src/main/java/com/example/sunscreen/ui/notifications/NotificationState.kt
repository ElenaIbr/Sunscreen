package com.example.sunscreen.ui.notifications

import com.example.domain.models.Notification
import com.example.domain.models.UserModel

data class NotificationState (
    val user: UserModel? = null,
    val notification: Notification? = null
)
