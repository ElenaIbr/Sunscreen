package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.Coordinates
import com.example.domain.models.ForecastModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.ForecastRepository
import com.example.domain.repositories.storage.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchForecastUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val forecastRepository: ForecastRepository,
    private val userRepository: UserRepository
) : FetchForecastUseCase,
    SingleUseCase<Coordinates, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: Coordinates): Resource<Unit> {
        return when (
            val result = remoteRepository.getForecast(
                latitude = input.latitude,
                longitude = input.longitude,
                date = input.date
            )
        ) {
            is Resource.Success -> {
                updateCoordinates(input)
                result.successData?.let { forecastModel ->
                    updateForecast(forecastModel)
                }
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error("")
            }
        }
    }
    private suspend fun updateForecast(forecastDay: ForecastModel) {
        forecastRepository.clear()
        forecastRepository.addValue(forecastDay)
    }
    private suspend fun updateCoordinates(coordinates: Coordinates) {
        userRepository.getUser()?.let { user ->
            userRepository.updateUser(
                user.copy(coordinates = coordinates)
            )
        }
    }
}
