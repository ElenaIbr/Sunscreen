package com.example.storage.index

import com.example.domain.models.IndexModel
import com.example.storage.StorageConverter
import javax.inject.Inject

class IndexConverter @Inject constructor(): StorageConverter<IndexStorageModel, IndexModel> {
    override fun convertToStorage(domainModel: IndexModel): IndexStorageModel {
        return with(domainModel) {
            IndexStorageModel(
                id = id,
                value = value,
                date = date,
                temperature = temperature,
                coordinates = coordinates,
                location = location
            )
        }
    }
    override fun convertFromStorage(storageModel: IndexStorageModel): IndexModel {
        return with(storageModel) {
            IndexModel(
                id = id,
                value = value,
                date = date,
                temperature = temperature,
                coordinates = coordinates,
                location = location
            )
        }
    }
}
