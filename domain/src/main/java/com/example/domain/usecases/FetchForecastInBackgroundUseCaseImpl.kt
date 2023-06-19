package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.ForecastRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FetchForecastInBackgroundUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val forecastRepository: ForecastRepository
) : FetchForecastInBackgroundUseCase,
    SingleUseCase<String, Unit>(Dispatchers.IO) {
    override suspend fun action(input: String) {
        if (forecastRepository.getAll().isEmpty()) {
            remoteRepository.startFetchForecastInBackground(input)
        }
    }
}
