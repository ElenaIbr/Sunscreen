package com.example.domain.repositories.storage

import com.example.domain.models.UserSettingsModel
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getUserSettingsFlow(): Flow<UserSettingsModel?>
    suspend fun getUserSettings(): UserSettingsModel?
    suspend fun addUserSettings(settingsStorageModel: UserSettingsModel)
    suspend fun updateUserSettings(settingsStorageModel: UserSettingsModel)
    suspend fun deleteUserSettings(settingsStorageModel: UserSettingsModel)
    suspend fun clear()
}
