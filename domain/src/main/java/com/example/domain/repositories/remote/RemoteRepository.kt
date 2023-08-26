package com.example.domain.repositories.remote

import com.example.domain.models.Coordinates
import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getWeather(coordinates: String): Resource<IndexModel>
    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        date: String
    ): Resource<ForecastModel>
    suspend fun getCurrentUvIndex(
        latitude: Double,
        longitude: Double,
        date: String
    ): Resource<Double>
    fun startFetchForecastInBackground(fetchForecastModel: Coordinates)
    fun getCurrentLocation(): Flow<String?>
}
