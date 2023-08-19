package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.FetchUvIndexModel
import com.example.domain.models.ForecastModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.ForecastRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchForecastUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val forecastRepository: ForecastRepository
) : FetchForecastUseCase,
    SingleUseCase<FetchUvIndexModel, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: FetchUvIndexModel): Resource<Unit> {
        return when (
            val result = remoteRepository.getForecast(
                latitude = input.latitude,
                longitude = input.longitude,
                date = input.date
            )
        ) {
            is Resource.Success -> {
                result.successData?.let { list ->
                    updateForecast(
                        listOf(list)
                    )
                }
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error("")
            }
        }
    }
    private suspend fun updateForecast(forecastDays: List<ForecastModel>) {
        forecastRepository.clear()
        forecastDays.sortedBy { it.date }.forEach { day ->
            forecastRepository.addValue(day)
        }
    }
}
