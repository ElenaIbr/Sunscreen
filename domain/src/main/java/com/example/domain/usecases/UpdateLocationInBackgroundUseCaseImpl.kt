package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.repositories.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateLocationInBackgroundUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
) : UpdateLocationInBackgroundUseCase,
    SingleUseCase<Unit, Unit>(Dispatchers.IO) {
    override suspend fun action(input: Unit): Unit {
        remoteRepository.startGetUpdateLocationWorker()
    }
}
