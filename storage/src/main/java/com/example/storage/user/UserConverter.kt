package com.example.storage.user

import com.example.domain.models.UserModel
import com.example.storage.StorageConverter
import javax.inject.Inject

class UserConverter @Inject constructor(): StorageConverter<UserStorageModel, UserModel> {
    override fun convertToStorage(domainModel: UserModel): UserStorageModel {
        return with(domainModel) {
            UserStorageModel(
                id = id,
                name = name,
                birthDate = birthDate,
                skinType = skinType,
                skinColor = skinColor,
                coordinates = coordinates,
                notifications = notifications
            )
        }
    }

    override fun convertFromStorage(storageModel: UserStorageModel): UserModel {
        return with(storageModel) {
            UserModel(
                id = id,
                name = name,
                birthDate = birthDate,
                skinType = skinType,
                skinColor = skinColor,
                coordinates = coordinates,
                notifications = notifications
            )
        }
    }
}
