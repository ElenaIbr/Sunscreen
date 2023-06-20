package com.example.sunscreen.di

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.example.domain.repositories.remote.RemoteRepository
import com.example.remote.weather.ForecastMapper
import com.example.remote.weather.WeatherApi
import com.example.remote.weather.WeatherApiFactory
import com.example.remote.weather.WeatherMapper
import com.example.remote.weather.RemoteRepositoryImpl
import com.example.sunscreen.workers.FetchForecastWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
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
        @ApplicationContext context: Context,
        weatherApi: WeatherApi,
        weatherMapper: WeatherMapper,
        forecastMapper: ForecastMapper
    ): RemoteRepository {
        return RemoteRepositoryImpl(
            context = context,
            weatherApi = weatherApi,
            weatherMapper = weatherMapper,
            forecastMapper = forecastMapper,
            fetchForecastWorker = getFetchForecastWorkerBuilder()
        )
    }

    private fun getFetchForecastWorkerBuilder(): PeriodicWorkRequest.Builder {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        return PeriodicWorkRequest.Builder(
            FetchForecastWorker::class.java, 15, TimeUnit.MINUTES
        ).setConstraints(constraints)
    }
}
