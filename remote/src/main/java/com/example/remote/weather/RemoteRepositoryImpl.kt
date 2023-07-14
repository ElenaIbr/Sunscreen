package com.example.remote.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.domain.models.ForecastModel
import com.example.domain.models.IndexModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.utils.Resource
import com.example.remote.base.ApiNetworkResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val context: Context,
    private val weatherApi: WeatherApi,
    private val weatherMapper: WeatherMapper,
    private val forecastMapper: ForecastMapper,
    private val fetchForecastWorker: PeriodicWorkRequest.Builder,
    private val getLocationWorker: PeriodicWorkRequest.Builder
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

    override fun startGetLocationInBackground() {
        val data = Data.Builder().putString("nothing", "").build()
        val getLocationWork = getLocationWorker.setInputData(data).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "GetLocationWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            getLocationWork
        )
    }

    override fun getCurrentLocation(): Flow<String?> = callbackFlow {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }) {

            var locationCallback: LocationCallback? = null
            var fusedLocationClient: FusedLocationProviderClient? = null

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    for (lo in p0.locations) {
                        trySend("${lo.latitude}${lo.longitude}")
                    }
                }
            }
            locationCallback.let {
                val locationRequest = LocationRequest.create().apply {
                    interval = 10000
                    fastestInterval = 5000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    it,
                    Looper.getMainLooper()
                )
            }
        }
    }
}
