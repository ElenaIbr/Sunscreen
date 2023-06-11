package com.example.remote.weather

import com.example.remote.base.ApiNetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/forecast.json?key=f5c0e7b8fd6f4895ab7183946233004")
    suspend fun getWeather(
        @Query("q") coordinates: String,
        @Query("days") days: String
    ): ApiNetworkResult<WeatherResponse>
}
