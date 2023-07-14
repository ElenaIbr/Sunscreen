package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.IndexRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetLocationInBackgroundUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val indexRepository: IndexRepository
) : GetLocationInBackgroundUseCase,
    SingleUseCase<Unit, Unit>(Dispatchers.IO) {
    override suspend fun action(input: Unit) {
        if (indexRepository.getLastValue() == null) {
            remoteRepository.startGetLocationInBackground()
        }
    }
}
