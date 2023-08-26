package com.example.sunscreen.di

import com.example.domain.usecases.FetchUvUseCase
import com.example.domain.usecases.FetchUvUseCaseImpl
import com.example.domain.usecases.GetUserNameUseCase
import com.example.domain.usecases.GetUserNameUseCaseImpl
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.GetUserUseCaseImpl
import com.example.domain.usecases.GetUvValueUseCase
import com.example.domain.usecases.GetUvValueUseCaseImpl
import com.example.domain.usecases.UpdateLocationInBackgroundUseCase
import com.example.domain.usecases.UpdateLocationInBackgroundUseCaseImpl
import com.example.domain.usecases.UpdateNotificationsUseCase
import com.example.domain.usecases.UpdateNotificationsUseCaseImpl
import com.example.domain.usecases.UpdateUserUseCase
import com.example.domain.usecases.UpdateUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelUseCaseModule {
    @Binds
    abstract fun bindFetchUvUseCase(
        FetchUvUseCase: FetchUvUseCaseImpl
    ): FetchUvUseCase

    @Binds
    abstract fun bindGetUvValueUseCase(
        getUvValueUseCase: GetUvValueUseCaseImpl
    ): GetUvValueUseCase

    @Binds
    abstract fun bindGetUserNameUseCase(
        getUserNameUseCase: GetUserNameUseCaseImpl
    ): GetUserNameUseCase

    @Binds
    abstract fun bindUpdateUserUseCase(
        updateUserUseCase: UpdateUserUseCaseImpl
    ): UpdateUserUseCase


    @Binds
    abstract fun bindUpdateNotificationsUseCase(
        updateNotificationsUseCase: UpdateNotificationsUseCaseImpl
    ): UpdateNotificationsUseCase

    @Binds
    abstract fun bindGetUserUseCase(
        getUserUseCase: GetUserUseCaseImpl
    ): GetUserUseCase

    @Binds
    abstract fun bindUpdateLocationInBackgroundUseCase(
        updateLocationInBackgroundUseCase: UpdateLocationInBackgroundUseCaseImpl
    ): UpdateLocationInBackgroundUseCase
}
