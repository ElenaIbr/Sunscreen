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
        indexRepository.getLastValue().collect { flow ->
            flow?.value?.let { uvValue ->
                emit(
                    UvValueModel(
                        solarActivityLevel = getSolarActivityLevel(uvValue),
                        indexModel = flow
                    )
                )
            } ?: emit(null)
        }
    }

    private fun getSolarActivityLevel(uvValue: Double): UvValueModel.SolarActivityLevel {
        return when (uvValue) {
            in 0.0..2.0 -> UvValueModel.SolarActivityLevel.Low
            in 3.0..5.0 -> UvValueModel.SolarActivityLevel.Medium
            in 6.0..7.0 -> UvValueModel.SolarActivityLevel.High
            in 8.0..10.00 -> UvValueModel.SolarActivityLevel.VeryHigh
            else -> UvValueModel.SolarActivityLevel.VeryHigh
        }
    }
}
