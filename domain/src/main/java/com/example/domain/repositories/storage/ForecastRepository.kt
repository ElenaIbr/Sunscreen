package com.example.domain.repositories.storage

import com.example.domain.models.ForecastModel
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface ForecastRepository {
    fun getAllInFlow(): Flow<List<ForecastModel>>
    suspend fun getAll(): List<ForecastModel>
    fun getFirstValue(): Flow<ForecastModel?>
    fun getForecastByrDate(date: Instant): Flow<ForecastModel?>
    fun getForecastInFlow(): Flow<List<ForecastModel>>
    suspend fun addValue(forecastModel: ForecastModel)
    suspend fun updateValue(forecastModel: ForecastModel)
    suspend fun deleteValue(forecastModel: ForecastModel)
    suspend fun clear()
}
