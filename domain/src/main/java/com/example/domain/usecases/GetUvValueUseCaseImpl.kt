package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.models.UvValueModel
import com.example.domain.repositories.storage.IndexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUvValueUseCaseImpl @Inject constructor(
    private val indexRepository: IndexRepository
): GetUvValueUseCase,
   FlowUseCase<Unit, UvValueModel?>(Dispatchers.IO)
{
    override fun action(input: Unit): Flow<UvValueModel?> = flow {
        indexRepository.getLastValueInFlow().collect { flow ->
            flow?.let { indexModel ->
                emit(
                    UvValueModel(
                        indexModel = indexModel
                    )
                )
            }
        }
    }
}
