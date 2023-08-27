package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.services.InternetConnectivityService
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CheckIfInternetAvailableUseCaseImpl @Inject constructor(
    private val internetConnectivityService: InternetConnectivityService
) : CheckIfInternetAvailableUseCase,
    SingleUseCase<Unit, Resource<Boolean>>(Dispatchers.IO) {
    override suspend fun action(input: Unit): Resource<Boolean> {
        return Resource.Success(internetConnectivityService.isInternetAvailable())
    }
}
