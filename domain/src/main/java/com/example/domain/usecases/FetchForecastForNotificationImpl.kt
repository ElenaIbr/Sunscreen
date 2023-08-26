package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.ForecastModel
import com.example.domain.models.SolarActivity
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.ForecastRepository
import com.example.domain.repositories.storage.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchForecastForNotificationImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val forecastRepository: ForecastRepository,
    private val userRepository: UserRepository
) : FetchForecastForNotification,
    SingleUseCase<Unit, Resource<SolarActivity>>(Dispatchers.IO) {
    override suspend fun action(input: Unit): Resource<SolarActivity> {
        return userRepository.getUser()?.coordinates?.let { coordinates ->
            return when (
                val result = remoteRepository.getForecast(
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude,
                    date = coordinates.date
                )
            ) {
                is Resource.Success -> {
                    result.successData?.let { forecast ->
                        updateForecast(forecast)
                    }
                    Resource.Success(getSolarActivity(result.successData))
                }
                is Resource.Error -> {
                    Resource.Error("Unable fetch forecast")
                }
            }
        } ?: Resource.Error("No user found")
    }
    private suspend fun updateForecast(forecastDay: ForecastModel) {
        forecastRepository.clear()
        forecastRepository.addValue(forecastDay)
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
