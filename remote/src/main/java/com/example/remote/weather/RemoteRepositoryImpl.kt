package com.example.remote.weather

import com.example.domain.models.IndexModel
import com.example.domain.repositories.remote.RemoteRepository
import com.example.domain.utils.Resource
import com.example.remote.base.ApiNetworkResult
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val converter: WeatherMapper
): RemoteRepository {
    override suspend fun getWeather(coordinates: String): Resource<IndexModel> {
        return weatherApi.getWeather(coordinates, "1").let { response ->
            when (response) {
                is ApiNetworkResult.Success -> {
                    if (response.data != null) {
                        Resource.Success(converter.convertFromRemote(response.data))
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
}
