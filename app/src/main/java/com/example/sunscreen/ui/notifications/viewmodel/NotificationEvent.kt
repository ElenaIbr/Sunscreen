package com.example.sunscreen.ui.notifications.viewmodel

import com.example.domain.models.Notification

interface NotificationEvent

class SetNotifications(val notification: Notification): NotificationEvent

class UpdateNotifications: NotificationEvent
