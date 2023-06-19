package com.example.remote.weather

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.utils.Resource
import com.example.remote.base.ApiNetworkResult
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val context: Context,
    private val weatherApi: WeatherApi,
    private val weatherMapper: WeatherMapper,
    private val forecastMapper: ForecastMapper,
    private val fetchForecastWorker: PeriodicWorkRequest.Builder
    ): RemoteRepository {
    override suspend fun getWeather(coordinates: String): Resource<IndexModel> {
        return weatherApi.getCurrentWeather(coordinates).let { response ->
            when (response) {
                is ApiNetworkResult.Success -> {
                    if (response.data != null) {
                        Resource.Success(weatherMapper.convertFromRemote(response.data))
                    } else {
                        Resource.Error("Empty body")
                    }
                }
                is ApiNetworkResult.Error -> {
                    Resource.Error(response.message.toString())
                }
                is ApiNetworkResult.Exception -> {
                    Resource.Error(response.e.message.toString())
                }
            }
        }
    }
    override suspend fun getForecast(daysAmount: Int, coordinates: String): Resource<List<ForecastModel>> {
        return weatherApi.getForecast(coordinates, daysAmount.toString()).let { response ->
            when (response) {
                is ApiNetworkResult.Success -> {
                    if (response.data != null) {
                        Resource.Success(forecastMapper.convertFromRemote(response.data))
                    } else {
                        Resource.Error("Empty body")
                    }
                }
                is ApiNetworkResult.Error -> {
                    Resource.Error(response.message.toString())
                }
                is ApiNetworkResult.Exception -> {
                    Resource.Error(response.e.message.toString())
                }
            }
        }
    }
    override fun startFetchForecastInBackground(coordinates: String) {
        val data = Data.Builder().putString("coordinates", coordinates).build()
        val fetchForecastWork = fetchForecastWorker.setInputData(data).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "FetchForecastWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            fetchForecastWork
        )
    }
}
