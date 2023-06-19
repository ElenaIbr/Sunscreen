package com.example.domain.usecases

import android.util.Log
import com.example.domain.base.SingleUseCase
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
    SingleUseCase<String, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: String): Resource<Unit> {
        Log.d("sdfsdfsdfsdf", input)
        return when (
            val result = remoteRepository.getForecast(
                daysAmount = 3,
                coordinates = input
            )
        ) {
            is Resource.Success -> {
                result.successData?.let { list ->
                    Log.d("sdfsdfsdfsdf", list.toString())
                    updateForecast(list)
                }
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Log.d("sdfsdfsdfsdf", result.errorMessage)
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
