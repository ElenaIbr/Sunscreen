package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.FetchUvIndexModel
import com.example.domain.models.IndexModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.IndexRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

class FetchUvUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val indexRepository: IndexRepository
) : FetchUvUseCase,
    SingleUseCase<FetchUvIndexModel, Resource<Unit>>(Dispatchers.IO)
{
    override suspend fun action(input: FetchUvIndexModel): Resource<Unit> {
        return when (
            val result = remoteRepository.getCurrentUvIndex(
                latitude = input.latitude,
                longitude = input.longitude,
                date = input.date
            )
        ){
            is Resource.Success -> {
                indexRepository.clear()
                indexRepository.addValue(
                    IndexModel(
                        id = UUID.randomUUID(),
                        value = result.successData,
                        date = Instant.now(),
                        temperature = null,
                        location = null,
                        coordinates = input.latitude.toString()+input.longitude.toString()
                    )
                )
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Resource.Error(result.errorMessage)
            }
        }
    }
}
