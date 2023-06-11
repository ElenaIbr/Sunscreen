package com.example.remote.weather

import com.example.domain.models.IndexModel
import com.example.remote.base.RemoteConverter
import java.util.UUID
import javax.inject.Inject

class WeatherMapper @Inject constructor(
) : RemoteConverter<WeatherResponse, IndexModel> {
    override fun convertFromRemote(remoteModel: WeatherResponse): IndexModel {
        return IndexModel(
            id = UUID.randomUUID(),
            value = remoteModel.current?.uv,
            date = remoteModel.location?.localtimeEpoch,
            temperature = remoteModel.current?.temp,
            location = remoteModel.location?.name,
            forecast = remoteModel.forecast?.toHours()
        )
    }
    private fun WeatherResponse.ForecastDay.toHours(): List<IndexModel.Hour>? {
        var hour = 0
        return this.forecastDay?.first()?.hour?.map { hourData ->
            hour+=1
            IndexModel.Hour(
                hour = hour,
                uv = hourData.uv ?: 0.0
            )
        }
    }
}
