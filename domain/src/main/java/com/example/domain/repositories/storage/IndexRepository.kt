package com.example.domain.repositories.storage

import com.example.domain.models.IndexModel
import kotlinx.coroutines.flow.Flow

interface IndexRepository {
    fun getAll(): Flow<List<IndexModel>>
    fun getLastValueInFlow(): Flow<IndexModel?>
    suspend fun getLastValue(): IndexModel?
    fun getCurrentDayValue(currentDate: String): Flow<List<IndexModel>>
    suspend fun addValue(indexStorageModel: IndexModel)
    suspend fun updateValue(indexStorageModel: IndexModel)
    suspend fun deleteValue(indexStorageModel: IndexModel)
    suspend fun clear()
}
