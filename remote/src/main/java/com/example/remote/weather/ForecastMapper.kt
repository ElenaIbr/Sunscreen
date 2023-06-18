package com.example.remote.weather

import com.example.domain.models.ForecastModel
import com.example.remote.base.RemoteConverter
import javax.inject.Inject

class ForecastMapper @Inject constructor(
) : RemoteConverter<WeatherResponse, List<ForecastModel>> {
    override fun convertFromRemote(remoteModel: WeatherResponse): List<ForecastModel> {
        return remoteModel.forecast?.forecastDay?.map { forecastDay ->
            ForecastModel(
                date = forecastDay.dateEpoch,
                forecast = forecastDay.toForecastModelList()
            )
        } ?: emptyList()
    }
    private fun WeatherResponse.Forecast.ForecastDay.toForecastModelList(): List<ForecastModel.Hour>? {
        var hour = 0
        return this.hour?.map { forecastModel ->
            hour+=1
            ForecastModel.Hour(
                hour = hour.toString(),
                uv = forecastModel.uv ?: 0.0
            )
        }
    }
}
