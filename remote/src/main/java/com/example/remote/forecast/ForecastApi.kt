package com.example.remote.forecast

import com.example.remote.base.ApiNetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("/api/v1/api/uv")
    suspend fun getCurrentUvIndex(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("alt") alt: Int,
        @Query("dt") dt: String
    ): ApiNetworkResult<ForecastResponse>

    @GET("/api/v1/forecast")
    suspend fun getUvIndexForecast(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("alt") alt: Int,
        @Query("dt") dt: String
    ): ApiNetworkResult<ForecastResponse>
}
