package com.example.domain.repositories.storage

import com.example.domain.models.IndexModel
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface IndexRepository {
    fun getAll(): Flow<List<IndexModel>>
    fun getLastValue(): Flow<IndexModel?>
    fun getCurrentDayValue(currentDate: String): Flow<List<IndexModel>>
    suspend fun addValue(indexStorageModel: IndexModel)
    suspend fun updateValue(indexStorageModel: IndexModel)
    suspend fun deleteValue(indexStorageModel: IndexModel)
    suspend fun clear()
}
