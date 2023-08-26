package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.UserModel
import com.example.domain.repositories.storage.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UpdateUserUseCase,
    SingleUseCase<UserModel, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: UserModel): Resource<Unit> {
        val currentUser = userRepository.getUser()
        if (currentUser != null) {
            userRepository.updateUser(
                currentUser.copy(
                    name = input.name,
                    birthDate = input.birthDate,
                    skinType = input.skinType,
                    skinColor = input.skinColor
                )
            )
        } else {
            userRepository.addUser(input)
        }
        return Resource.Success(Unit)
    }
}
