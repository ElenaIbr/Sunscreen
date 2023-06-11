package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.models.UserModel
import com.example.domain.repositories.storage.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetUserUseCase,
    FlowUseCase<Unit, GetUserEntity>(Dispatchers.IO) {
    override fun action(input: Unit): Flow<GetUserEntity> = flow {
        emit(GetUserEntity.Loading)
        userRepository.getUserFlow().collect { user ->
            emit(GetUserEntity.Success(user))
        }
    }
}

sealed class GetUserEntity {
    object Loading : GetUserEntity()
    data class Success(val userModel: UserModel?) : GetUserEntity()
}
