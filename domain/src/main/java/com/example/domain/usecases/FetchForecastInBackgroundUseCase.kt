package com.example.domain.usecases

import com.example.domain.base.SingleUseCaseInterface
import com.example.domain.utils.Resource

interface FetchForecastInBackgroundUseCase : SingleUseCaseInterface<Unit, Resource<Unit>>
