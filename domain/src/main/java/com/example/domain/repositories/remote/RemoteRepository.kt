package com.example.domain.repositories.remote

import com.example.domain.models.FetchForecastModel
import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface RemoteRepository {
    suspend fun getWeather(coordinates: String): Resource<IndexModel>
    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        date: String
    ): Resource<ForecastModel>
    fun startFetchForecastInBackground(fetchForecastModel: FetchForecastModel)
    fun startGetLocationInBackground()
    fun getCurrentLocation(): Flow<String?>
}
