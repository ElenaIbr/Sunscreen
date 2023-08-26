package com.example.domain.usecases

import com.example.domain.base.SingleUseCase
import com.example.domain.models.UserModel
import com.example.domain.repositories.storage.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetUserSingleUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserSingleUseCase,
    SingleUseCase<Unit, Resource<UserModel>>(Dispatchers.IO) {
    override suspend fun action(input: Unit): Resource<UserModel> {
        return userRepository.getUser()?.let { user ->
            Resource.Success(user)
        } ?: run {
            Resource.Error("User not found")
        }
    }
}
