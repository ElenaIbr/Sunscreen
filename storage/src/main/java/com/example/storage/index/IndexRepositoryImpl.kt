package com.example.storage.index

import com.example.domain.models.IndexModel
import com.example.domain.repositories.storage.IndexRepository
import com.example.storage.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IndexRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val converter: IndexConverter
) : IndexRepository {
    override fun getAll(): Flow<List<IndexModel>> {
        return appDatabase.indexDao().getAll().map { list ->
            list.map { converter.convertFromStorage(it) }
        }
    }

    override fun getLastValueInFlow(): Flow<IndexModel?> {
        return appDatabase.indexDao().getLastValueInFlow().map { index ->
            index?.let {
                converter.convertFromStorage(index)
            } ?: run { null }
        }
    }

    override suspend fun getLastValue(): IndexModel? {
        return appDatabase.indexDao().getLastValue()?.let { index ->
            converter.convertFromStorage(index)
        }
    }

    override fun getCurrentDayValue(currentDate: String): Flow<List<IndexModel>> {
        return appDatabase.indexDao().getCurrentDayValue(currentDate).map { list ->
            list.map { converter.convertFromStorage(it) }
        }
    }


    override suspend fun addValue(indexStorageModel: IndexModel) {
        appDatabase.indexDao().addValue(
            converter.convertToStorage(indexStorageModel)
        )
    }

    override suspend fun updateValue(indexStorageModel: IndexModel) {
        appDatabase.indexDao().updateValue(
            converter.convertToStorage(indexStorageModel)
        )
    }

    override suspend fun deleteValue(indexStorageModel: IndexModel) {
        appDatabase.indexDao().deleteValue(
            converter.convertToStorage(indexStorageModel)
        )
    }

    override suspend fun clear() {
        appDatabase.indexDao().clear()
    }
}
