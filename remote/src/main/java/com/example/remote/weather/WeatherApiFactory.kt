package com.example.remote.weather

import com.example.remote.base.NetworkResultCallAdapterFactory
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
}
