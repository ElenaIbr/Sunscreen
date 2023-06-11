package com.example.storage.user

import com.example.domain.models.UserModel
import com.example.domain.repositories.storage.UserRepository
import com.example.storage.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val converter: UserConverter
) : UserRepository {
    override fun getUserFlow(): Flow<UserModel?> {
        return appDatabase.userDao().getUserFlow().map { user ->
            user?.let {
                converter.convertFromStorage(user)
            } ?: run { null }
        }
    }

    override suspend fun getUser(): UserModel? {
        return appDatabase.userDao().getUser()?.let { user ->
            converter.convertFromStorage(user)
        }
    }

    override suspend fun addUser(personStorageModel: UserModel) {
        appDatabase.userDao().addUser(
            converter.convertToStorage(personStorageModel)
        )
    }

    override suspend fun updateUser(personStorageModel: UserModel) {
        appDatabase.userDao().addUser(
            converter.convertToStorage(personStorageModel)
        )
    }

    override suspend fun deleteUser(personStorageModel: UserModel) {
        appDatabase.userDao().updateUser(
            converter.convertToStorage(personStorageModel)
        )
    }

    override suspend fun clear() {
        appDatabase.userDao().clear()
    }
}
