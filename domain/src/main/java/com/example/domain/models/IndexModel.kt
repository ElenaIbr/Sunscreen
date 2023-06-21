package com.example.domain.models

import java.util.UUID

data class IndexModel (
    val id: UUID,
    val value: Double?,
    val date: Long?,
    val temperature: Double?,
    val location: String?,
    val coordinates: String?
)
