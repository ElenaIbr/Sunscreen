package com.example.domain.usecases

import com.example.domain.base.FlowUseCaseInterface
import com.example.domain.models.ForecastModel
import java.time.Instant

interface GetForecastByDateUseCase : FlowUseCaseInterface<Instant, List<ForecastModel.Hour>?>
