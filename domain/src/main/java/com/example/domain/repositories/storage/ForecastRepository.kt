package com.example.domain.repositories.storage

import com.example.domain.models.ForecastModel
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    fun getAllInFlow(): Flow<List<ForecastModel>>
    suspend fun getAll(): List<ForecastModel>
    fun getFirstValue(): Flow<ForecastModel?>
    suspend fun addValue(forecastModel: ForecastModel)
    suspend fun updateValue(forecastModel: ForecastModel)
    suspend fun deleteValue(forecastModel: ForecastModel)
    suspend fun clear()
}