package com.example.domain.usecases

import android.util.Log
import com.example.domain.base.SingleUseCase
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.repositories.storage.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateLocationUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val userRepository: UserRepository
) : UpdateLocationUseCase,
    SingleUseCase<Unit, Resource<Unit>>(Dispatchers.IO) {
    override suspend fun action(input: Unit): Resource<Unit> {
        return try {
            remoteRepository.getLocation().collect { coordinates ->
                userRepository.updateUserCoordinates(coordinates)
            }
            Resource.Success(Unit)
        } catch(e: Exception) {
            Log.e("UpdateLocationUseCase", e.message.toString())
            Resource.Error("Unable update location")
        }
    }
}
