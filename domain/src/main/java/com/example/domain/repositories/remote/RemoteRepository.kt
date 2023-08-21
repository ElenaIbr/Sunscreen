package com.example.domain.repositories.remote

import com.example.domain.models.FetchUvIndexModel
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
    fun startFetchForecastInBackground(fetchForecastModel: FetchUvIndexModel)
    fun getCurrentLocation(): Flow<String?>
}
