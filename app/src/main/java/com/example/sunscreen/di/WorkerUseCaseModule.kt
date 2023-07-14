package com.example.sunscreen.di

import com.example.domain.usecases.FetchForecastUseCase
import com.example.domain.usecases.FetchForecastUseCaseImpl
import com.example.domain.usecases.UpdateLocationUseCase
import com.example.domain.usecases.UpdateLocationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerUseCaseModule {
    @Binds
    abstract fun bindFetchForecastUseCase(
        fetchForecastUseCase: FetchForecastUseCaseImpl
    ): FetchForecastUseCase

    @Binds
    abstract fun bindUpdateLocationUseCase(
        updateLocationUseCase: UpdateLocationUseCaseImpl
    ): UpdateLocationUseCase
}
