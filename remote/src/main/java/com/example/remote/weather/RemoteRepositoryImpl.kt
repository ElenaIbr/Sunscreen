package com.example.remote.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.domain.models.Coordinates
import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.utils.Resource
import com.example.remote.base.ApiNetworkResult
import com.example.remote.forecast.ForecastApi
import com.example.remote.forecast.ForecastMapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.Instant
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val context: Context,
    private val weatherApi: WeatherApi,
    private val forecastApi: ForecastApi,
    private val weatherMapper: WeatherMapper,
    private val forecastMapper: ForecastMapper,
    private val fetchForecastWorker: PeriodicWorkRequest.Builder,
    private val getUpdateLocationWorker: PeriodicWorkRequest.Builder
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
    override suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        date: String
    ): Resource<ForecastModel> {
        return forecastApi.getUvIndexForecast(
            lat = latitude,
            lng = longitude,
            alt = 100,
            dt = date
        ).let { response ->
            Resource.Success(
                ForecastModel(
                    date = Instant.now(),
                    forecast = listOf(
                        ForecastModel.Hour(
                            hour = "1",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "2",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "3",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "4",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "5",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "6",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "7",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "8",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "9",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "10",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "11",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "12",
                            uv = 10.00
                        ),
                        ForecastModel.Hour(
                            hour = "13",
                            uv = 11.00
                        ),
                        ForecastModel.Hour(
                            hour = "14",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "15",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "16",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "17",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "18",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "19",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "20",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "21",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "22",
                            uv = 0.01
                        ),
                        ForecastModel.Hour(
                            hour = "23",
                            uv = 0.01
                        )
                    )
                )
            )
            /*when (response) {
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
            }*/
        }
    }
    override suspend fun getCurrentUvIndex(
        latitude: Double,
        longitude: Double,
        date: String
    ): Resource<Double> {
        return forecastApi.getCurrentUvIndex(
            lat = latitude,
            lng = longitude,
            alt = 100,
            dt = date
        ).let { response ->
            Resource.Success(
                9.0
            )
            /*when (response) {
                is ApiNetworkResult.Success -> {
                    if (response.data != null) {
                        Resource.Success(response.data.result?.uv)
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
            }*/
        }
    }
    override fun startFetchForecastInBackground(fetchForecastModel: Coordinates) {
        val data = Data.Builder()
            .putDouble("latitude", fetchForecastModel.latitude)
            .putDouble("longitude", fetchForecastModel.longitude)
            .putString("date", fetchForecastModel.date)
            .build()
        val fetchForecastWork = fetchForecastWorker.setInputData(data).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "FetchForecastWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            fetchForecastWork
        )
    }
    override fun startGetUpdateLocationWorker() {
        val getUpdateLocationWorker = getUpdateLocationWorker.build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "GetUpdateLocationWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            getUpdateLocationWorker
        )
    }
    override fun getLocation(): Flow<Coordinates> = callbackFlow {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }) {
            var fusedLocationClient: FusedLocationProviderClient? = null
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    location?.let { locationValue ->
                        Log.d("adsfsf", locationValue.toString())
                        trySend(
                            Coordinates(
                                latitude = locationValue.latitude,
                                longitude = locationValue.longitude,
                                date = ""
                            )
                        )
                    }
                }
        }
        awaitClose()
    }
}
