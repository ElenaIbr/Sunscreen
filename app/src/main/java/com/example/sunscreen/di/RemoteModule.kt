package com.example.sunscreen.di

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import com.example.domain.repositories.remote.RemoteRepository
import com.example.remote.forecast.ForecastApi
import com.example.remote.forecast.ForecastMapper
import com.example.remote.interceptor.AuthenticationInterceptor
import com.example.domain.services.InternetConnectivityService
import com.example.remote.services.InternetConnectivityServiceImpl
import com.example.remote.weather.WeatherApi
import com.example.remote.weather.WeatherApiFactory
import com.example.remote.weather.WeatherMapper
import com.example.remote.weather.RemoteRepositoryImpl
import com.example.domain.services.NotificationsManager
import com.example.sunscreen.services.NotificationsManagerImpl
import com.example.sunscreen.workers.FetchForecastWorker
import com.example.sunscreen.workers.UpdateLocationWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
    }

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return WeatherApiFactory.createWeatherApi()
    }

    @Singleton
    @Provides
    fun provideForecastApi(
        okHttpClient: OkHttpClient.Builder,
        authenticationInterceptor: AuthenticationInterceptor
    ): ForecastApi {
        return WeatherApiFactory.createForecastApi(
            okHttpClient,
            authenticationInterceptor
        )
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        @ApplicationContext context: Context,
        weatherApi: WeatherApi,
        forecastApi: ForecastApi,
        weatherMapper: WeatherMapper,
        forecastMapper: ForecastMapper
    ): RemoteRepository {
        return RemoteRepositoryImpl(
            context = context,
            weatherApi = weatherApi,
            forecastApi = forecastApi,
            weatherMapper = weatherMapper,
            forecastMapper = forecastMapper,
            fetchForecastWorker = getFetchForecastWorkerBuilder(),
            getUpdateLocationWorker = getUpdateLocationWorkerBuilder()
        )
    }

    private fun getFetchForecastWorkerBuilder(): PeriodicWorkRequest.Builder {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        return PeriodicWorkRequest.Builder(
            FetchForecastWorker::class.java, 24, TimeUnit.HOURS
        ).setConstraints(constraints)
    }

    private fun getUpdateLocationWorkerBuilder(): PeriodicWorkRequest.Builder {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        return PeriodicWorkRequest.Builder(
            UpdateLocationWorker::class.java, 15, TimeUnit.MINUTES
        ).setConstraints(constraints)
    }

    @Singleton
    @Provides
    fun bindNetworkMonitorService(
        @ApplicationContext context: Context,
    ): InternetConnectivityService {
        return InternetConnectivityServiceImpl(context)
    }

    @Singleton
    @Provides
    fun bindNotificationsManager(
        @ApplicationContext context: Context,
    ): NotificationsManager {
        return NotificationsManagerImpl(context)
    }
}
