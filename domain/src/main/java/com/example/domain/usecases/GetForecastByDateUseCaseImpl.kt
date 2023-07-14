package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.models.ForecastModel
import com.example.domain.repositories.storage.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit


class GetForecastByDateUseCaseImpl @Inject constructor(
    private val forecastRepository: ForecastRepository
):  GetForecastByDateUseCase,
    FlowUseCase<Instant, List<ForecastModel.Hour>?>(Dispatchers.IO) {
    override fun action(input: Instant): Flow<List<ForecastModel.Hour>?> = flow {
        val current = Calendar.getInstance()
        val currentHour = current.get(Calendar.HOUR_OF_DAY).hours.toInt(DurationUnit.HOURS)

        forecastRepository.getForecastByrDate(input).collect { forecastModel ->
            emit(
                forecastModel?.forecast?.filter { forecast ->
                    forecast.hour.toInt() >= currentHour
                }
            )
        }
    }
}
