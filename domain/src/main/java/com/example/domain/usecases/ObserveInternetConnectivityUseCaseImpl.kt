package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.models.InternetConnectivityEntity
import com.example.domain.services.InternetConnectivityService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObserveInternetConnectivityUseCaseImpl @Inject constructor(
    private val internetConnectivityService: InternetConnectivityService
) : ObserveInternetConnectivityUseCase, FlowUseCase<Unit, InternetConnectivityEntity>(Dispatchers.IO) {
    override fun action(input: Unit): Flow<InternetConnectivityEntity> = flow {
        if (!internetConnectivityService.isInternetAvailable()) {
            emit(
                InternetConnectivityEntity.Unavailable
            )
        }
        internetConnectivityService.observeInternetConnectivity().collect { status ->
            emit(status)
        }
    }
}
