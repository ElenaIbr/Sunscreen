package com.example.storage

interface StorageConverter<StorageModel, DomainModel> {
    fun convertToStorage(domainModel: DomainModel): StorageModel
    fun convertFromStorage(storageModel: StorageModel): DomainModel
}
