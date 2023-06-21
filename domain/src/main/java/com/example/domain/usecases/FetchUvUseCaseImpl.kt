package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.entities.FetchUvEntity
import com.example.domain.models.IndexModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.IndexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchUvUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val indexRepository: IndexRepository
) : FetchUvUseCase,
    FlowUseCase<String, FetchUvEntity>(Dispatchers.IO)
{
    override fun action(input: String): Flow<FetchUvEntity> = flow {
        emit(FetchUvEntity.Loading)

        val result = remoteRepository.getWeather(input).data
        if (result != null) {
            indexRepository.addValue(
                IndexModel(
                    id = result.id,
                    value = result.value,
                    date = result.date,
                    temperature = result.temperature,
                    location = result.location,
                    coordinates = input
                )
            )
            emit(FetchUvEntity.Success)
        } else {
            emit(FetchUvEntity.Failure(""))
        }
    }
}
