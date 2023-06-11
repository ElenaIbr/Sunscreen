package com.example.domain.usecases

import com.example.domain.base.FlowUseCase
import com.example.domain.repositories.storage.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserNameUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetUserNameUseCase,
   FlowUseCase<Unit, String?>(Dispatchers.IO) {
    override fun action(input: Unit): Flow<String?> = flow {
        userRepository.getUserFlow().collect { user ->
            emit(if (user?.name != null) user.name else "")
        }
    }
}
