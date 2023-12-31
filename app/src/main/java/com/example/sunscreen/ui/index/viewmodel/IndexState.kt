package com.example.sunscreen.ui.index.viewmodel

import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.models.SolarActivity

data class IndexState(
    val userName: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val index: IndexModel? = null,
    val indexValue: Double? = null,
    val date: String? = null,
    val forecast: ForecastModel? = null,
    val solarActivityLevel: SolarActivity? = null,
    val isLoading: Boolean = true,
    val isForecastLoading: Boolean = true,
    val isInternetAvailable: Boolean = true
)
