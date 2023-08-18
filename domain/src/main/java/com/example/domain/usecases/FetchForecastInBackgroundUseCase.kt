package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.models.FetchForecastModel

interface FetchForecastInBackgroundUseCase : SingleUseCaseInterface<FetchForecastModel, Unit>
