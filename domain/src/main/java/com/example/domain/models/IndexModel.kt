package com.example.domain.models

import java.time.Instant
import java.util.UUID

data class IndexModel (
    val id: UUID,
    val value: Double?,
    val date: Instant?,
    val temperature: Double?,
    val location: String?,
    val coordinates: String?
)
