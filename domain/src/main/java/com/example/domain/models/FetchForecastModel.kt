package com.example.domain.models

import java.time.Instant

data class FetchForecastModel (
    val latitude: Double,
    val longitude: Double,
    val date: String
)
