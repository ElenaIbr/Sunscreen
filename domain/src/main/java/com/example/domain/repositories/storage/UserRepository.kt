package com.example.domain.repositories.storage

import com.example.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserFlow(): Flow<UserModel?>
    suspend fun getUser(): UserModel?
    suspend fun addUser(personStorageModel: UserModel)
    suspend fun updateUser(personStorageModel: UserModel)
    suspend fun deleteUser(personStorageModel: UserModel)
    suspend fun clear()
}
