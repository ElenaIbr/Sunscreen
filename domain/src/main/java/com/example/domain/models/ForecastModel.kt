package com.example.domain.models

import java.time.Instant

data class ForecastModel (
    val date: Instant?,
    val forecast: List<Hour>?
) {
    data class Hour (
        val hour: String,
        val uv: Double
    )
}
