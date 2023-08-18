package com.example.remote.weather

import com.example.remote.base.NetworkResultCallAdapterFactory
import com.example.remote.forecast.ForecastApi
import com.example.remote.interceptor.AuthenticationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiFactory {
    fun createWeatherApi(): WeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
        return retrofit.create(WeatherApi::class.java)
    }
    fun createForecastApi(
        okHttpBuilder: OkHttpClient.Builder,
        authenticationInterceptor: AuthenticationInterceptor
    ): ForecastApi {
        val okHttpClient = okHttpBuilder
            .addNetworkInterceptor(authenticationInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openuv.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ForecastApi::class.java)
    }
}
