package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.IndexRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

class UpdateLocationUseCaseImpl @Inject constructor(
    private val indexRepository: IndexRepository,
    private val remoteRepository: RemoteRepository
) : UpdateLocationUseCase,
    SingleUseCase<Unit, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: Unit): Resource<Unit> {
        return try {
            indexRepository.getLastValue()?.let { indexModel ->
                remoteRepository.getCurrentLocation().collect { coordinates ->
                    indexRepository.updateValue(
                        indexModel.copy(
                            coordinates = coordinates
                        )
                    )
                }
            }
            Resource.Success(Unit)
        } catch(e: Exception) {
            Resource.Error("UpdateLocationUseCaseImpl")
        }
    }
}
