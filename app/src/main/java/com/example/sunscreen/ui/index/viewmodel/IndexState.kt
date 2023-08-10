package com.example.sunscreen.ui.index.viewmodel

import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.models.UvValueModel

data class IndexState(
    val userName: String? = null,
    val weather: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val index: IndexModel? = null,
    val date: String? = null,
    val temperature: String? = null,
    val forecast: List<ForecastModel.Hour>? = null,
    val solarActivityLevel: UvValueModel.SolarActivityLevel? = null,
    val isLoading: Boolean = true
)