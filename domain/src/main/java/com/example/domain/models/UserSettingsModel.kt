package com.example.domain.models

import java.time.Instant
import java.util.UUID

data class UserSettingsModel (
    val id: UUID,
    val notificationEnabled: Boolean,
    val notificationTime: String
)
