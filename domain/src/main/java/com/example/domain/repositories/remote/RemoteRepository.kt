package com.example.domain.repositories.remote

import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.utils.Resource

interface RemoteRepository {
    suspend fun getWeather(coordinates: String): Resource<IndexModel>
    suspend fun getForecast(daysAmount: Int, coordinates: String): Resource<List<ForecastModel>>
}
