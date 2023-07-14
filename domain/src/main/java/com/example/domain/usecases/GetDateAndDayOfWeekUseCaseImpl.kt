package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.repositories.storage.IndexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Locale
import javax.inject.Inject

class GetDateAndDayOfWeekUseCaseImpl @Inject constructor(
    private val indexRepository: IndexRepository
):  GetDateAndDayOfWeekUseCase,
    FlowUseCase<Unit, String?>(Dispatchers.IO) {
    override fun action(input: Unit): Flow<String?> = flow {
        indexRepository.getLastValueInFlow().collect { indexModel ->
            /*val date = indexModel?.date?.convertEpochToInstant()
            val dayOfWeek = date?.dayOfWeek?.name?.lowercase()
            val month = date?.month?.name?.lowercase()
            emit(
                "${dayOfWeek?.capitalize(Locale.ROOT)}, ${date?.dayOfMonth} ${month?.capitalize(Locale.ROOT)}"
            )*/
        }
    }
    private fun Long.convertEpochToInstant(): OffsetDateTime {
        return OffsetDateTime.of(
            LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC),
            ZoneOffset.UTC
        )
    }
}
