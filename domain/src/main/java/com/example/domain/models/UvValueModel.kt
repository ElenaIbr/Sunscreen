package com.example.domain.models

data class UvValueModel (
    val solarActivityLevel: SolarActivityLevel,
    val indexModel: IndexModel
) {
    enum class SolarActivityLevel {
        Low,
        Medium,
        High,
        VeryHigh
    }
}
