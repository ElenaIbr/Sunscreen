package com.example.sunscreen.di

import com.example.domain.repositories.remote.RemoteRepository
import com.example.remote.weather.WeatherApi
import com.example.remote.weather.WeatherApiFactory
import com.example.remote.weather.WeatherMapper
import com.example.remote.weather.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return WeatherApiFactory.createWeatherApi()
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        weatherApi: WeatherApi,
        converter: WeatherMapper
    ): RemoteRepository {
        return RemoteRepositoryImpl(weatherApi, converter)
    }
}
