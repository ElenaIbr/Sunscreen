package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.models.FetchUvIndexModel
import com.example.domain.utils.Resource

interface FetchForecastUseCase : SingleUseCaseInterface<FetchUvIndexModel, Resource<Unit>>
