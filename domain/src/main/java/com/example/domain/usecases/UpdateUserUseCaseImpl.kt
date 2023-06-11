package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.UserModel
import com.example.domain.repositories.storage.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.util.UUID
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UpdateUserUseCase,
    SingleUseCase<UserModel, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: UserModel): Resource<Unit> {
        return try {
            val currentUser = userRepository.getUser()
            userRepository.updateUser(
                input.copy(
                    id = currentUser?.id ?: UUID.randomUUID()
                )
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("")
        }
    }
}
