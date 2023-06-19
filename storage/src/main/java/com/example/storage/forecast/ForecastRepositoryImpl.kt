package com.example.storage.forecast

import com.example.domain.models.ForecastModel
import com.example.domain.repositories.storage.ForecastRepository
import com.example.storage.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val converter: ForecastConverter
) : ForecastRepository {
    override fun getAllInFlow(): Flow<List<ForecastModel>> {
        return appDatabase.forecastDao().getAllInFlow().map { list ->
            list.map { converter.convertFromStorage(it) }
        }
    }

    override suspend fun getAll(): List<ForecastModel> {
        return appDatabase.forecastDao().getAll().map { list ->
            converter.convertFromStorage(list)
        }
    }

    override fun getFirstValue(): Flow<ForecastModel?> {
        return appDatabase.forecastDao().getFirstValue().map { index ->
            index?.let {
                converter.convertFromStorage(index)
            } ?: run { null }
        }
    }
    override suspend fun addValue(forecastModel: ForecastModel) {
        appDatabase.forecastDao().addValue(
            converter.convertToStorage(forecastModel)
        )
    }
    override suspend fun updateValue(forecastModel: ForecastModel) {
        appDatabase.forecastDao().updateValue(
            converter.convertToStorage(forecastModel)
        )
    }
    override suspend fun deleteValue(forecastModel: ForecastModel) {
        appDatabase.forecastDao().deleteValue(
            converter.convertToStorage(forecastModel)
        )
    }
    override suspend fun clear() {
        appDatabase.forecastDao().clear()
    }
}
