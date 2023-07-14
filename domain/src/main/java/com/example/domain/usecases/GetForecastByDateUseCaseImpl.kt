package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.models.ForecastModel
import com.example.domain.repositories.storage.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

class GetForecastByDateUseCaseImpl @Inject constructor(
    private val forecastRepository: ForecastRepository
):  GetForecastByDateUseCase,
    FlowUseCase<Instant, ForecastModel?>(Dispatchers.IO) {
    override fun action(input: Instant): Flow<ForecastModel?> = flow {
        forecastRepository.getForecastByrDate(input).collect { forecastModel ->
            emit(forecastModel)
        }
    }
}
