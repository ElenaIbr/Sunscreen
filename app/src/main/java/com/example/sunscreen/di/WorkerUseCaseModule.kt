package com.example.sunscreen.di

import com.example.domain.usecases.CheckIfInternetAvailableUseCase
import com.example.domain.usecases.CheckIfInternetAvailableUseCaseImpl
import com.example.domain.usecases.FetchForecastForNotification
import com.example.domain.usecases.FetchForecastForNotificationImpl
import com.example.domain.usecases.FetchForecastUseCase
import com.example.domain.usecases.FetchForecastUseCaseImpl
import com.example.domain.usecases.GetForecastByDateUseCase
import com.example.domain.usecases.GetForecastByDateUseCaseImpl
import com.example.domain.usecases.GetUserSingleUseCase
import com.example.domain.usecases.GetUserSingleUseCaseImpl
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

    @Binds
    abstract fun bindGetForecastByDateUseCase(
        getForecastByDateUseCase: GetForecastByDateUseCaseImpl
    ): GetForecastByDateUseCase

    @Binds
    abstract fun bindGetUserSingleUseCase(
        getUserSingleUseCase: GetUserSingleUseCaseImpl
    ): GetUserSingleUseCase

    @Binds
    abstract fun bindFetchForecastForNotification(
        fetchForecastForNotification: FetchForecastForNotificationImpl
    ): FetchForecastForNotification

    @Binds
    abstract fun bindCheckIfInternetAvailableUseCase(
        checkIfInternetAvailableUseCase: CheckIfInternetAvailableUseCaseImpl
    ): CheckIfInternetAvailableUseCase
}
