package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.models.ForecastModel
import com.example.domain.repositories.storage.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject


class GetForecastByDateUseCaseImpl @Inject constructor(
    private val forecastRepository: ForecastRepository
):  GetForecastByDateUseCase,
    FlowUseCase<LocalDateTime, ForecastModel?>(Dispatchers.IO) {
    override fun action(input: LocalDateTime): Flow<ForecastModel?> = flow {
        forecastRepository.getAllInFlow().collect { forecast ->
            emit(
                forecast.sortedBy { it.date }.firstOrNull()
            )
        }
    }
}
