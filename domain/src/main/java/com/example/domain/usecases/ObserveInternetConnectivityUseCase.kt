package com.example.domain.usecases

import com.example.domain.base.FlowUseCaseInterface
import com.example.domain.models.InternetConnectivityEntity

interface ObserveInternetConnectivityUseCase : FlowUseCaseInterface<Unit, InternetConnectivityEntity>
