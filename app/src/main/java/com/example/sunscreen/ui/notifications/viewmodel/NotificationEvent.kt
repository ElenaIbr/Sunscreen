package com.example.sunscreen.ui.notifications.viewmodel

import com.example.domain.models.Notification

interface NotificationEvent

class ChangeNotifications(val notification: Notification): NotificationEvent

class SetNotifications(): NotificationEvent

