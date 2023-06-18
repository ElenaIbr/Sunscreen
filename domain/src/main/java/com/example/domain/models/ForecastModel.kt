package com.example.domain.models

data class ForecastModel (
    val date: Long?,
    val forecast: List<Hour>?
) {
    data class Hour (
        val hour: String,
        val uv: Double
    )
}
