package com.example.domain.models

data class ForecastDay (
    val hours: List<Hour>
) {
    data class Hour (
        val hour: String,
        val uv: Double
    )
}
