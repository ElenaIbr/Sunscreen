package com.example.domain.repositories.remote

import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getWeather(coordinates: String): Resource<IndexModel>
    suspend fun getForecast(daysAmount: Int, coordinates: String): Resource<ForecastModel>
    fun startFetchForecastInBackground(coordinates: String)
    fun startGetLocationInBackground()
    fun getCurrentLocation(): Flow<String?>
}
