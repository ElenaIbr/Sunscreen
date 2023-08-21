package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.ForecastModel
import com.example.domain.models.SolarActivity
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.ForecastRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchForecastForNotificationImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val forecastRepository: ForecastRepository
) : FetchForecastForNotification,
    SingleUseCase<String, Resource<SolarActivity>>(Dispatchers.IO) {
    override suspend fun action(input: String): Resource<SolarActivity> {
        return when (
            val result = remoteRepository.getForecast(
                latitude = 52.050757,
                longitude = 4.3394762,
                date = input
            )
        ) {
            is Resource.Success -> {
                result.successData?.let { list ->
                    updateForecast(
                        listOf(list)
                    )
                }
                Resource.Success(getSolarActivity(result.successData))
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
    private fun getSolarActivity(forecastModel: ForecastModel?): SolarActivity {
        return forecastModel?.forecast?.maxBy { it.uv }?.uv?.let { value ->
            when (value) {
                in 0.0..3.0 -> SolarActivity.Low
                in 3.0..5.0 -> SolarActivity.Medium
                in 5.0..8.0 -> SolarActivity.High
                in 8.0..10.00 -> SolarActivity.VeryHigh
                else -> SolarActivity.VeryHigh
            }
        } ?: SolarActivity.Low
    }
}
