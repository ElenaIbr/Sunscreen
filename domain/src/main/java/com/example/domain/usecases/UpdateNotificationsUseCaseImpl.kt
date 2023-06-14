package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.Notification
import com.example.domain.repositories.storage.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateNotificationsUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UpdateNotificationsUseCase,
    SingleUseCase<Notification, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: Notification): Resource<Unit> {
        return try {
            val currentUser = userRepository.getUser()
            currentUser?.let { storedUser ->
                userRepository.updateUser(
                    storedUser.copy(
                        notifications = input
                    )
                )
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("")
        }
    }
}
