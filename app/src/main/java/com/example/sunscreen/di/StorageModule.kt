package com.example.sunscreen.di

import com.example.domain.repositories.storage.IndexRepository
import com.example.domain.repositories.storage.UserRepository
import com.example.storage.index.IndexRepositoryImpl
import com.example.storage.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun bindIndexRepository(
        indexRepository: IndexRepositoryImpl
    ): IndexRepository

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}
