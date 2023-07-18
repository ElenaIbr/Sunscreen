package com.example.remote.weather

import com.example.domain.models.ForecastModel
import com.example.remote.base.RemoteConverter
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject


class ForecastMapper @Inject constructor(
) : RemoteConverter<WeatherResponse, List<ForecastModel>> {
    override fun convertFromRemote(remoteModel: WeatherResponse): List<ForecastModel> {
        return remoteModel.forecast?.forecastDay?.map { forecastDay ->
            val instant = Instant.ofEpochSecond(forecastDay.dateEpoch ?: Instant.now().toEpochMilli()).atZone(
                ZoneId.of(remoteModel.location?.tzId)
            ).toInstant()

            ForecastModel(
                date = instant.truncatedTo(ChronoUnit.DAYS),
                forecast = forecastDay.toForecastModelList(remoteModel.location?.tzId)
            )
        } ?: emptyList()
    }
    private fun WeatherResponse.Forecast.ForecastDay.toForecastModelList(tzId: String?): List<ForecastModel.Hour>? {
        return this.hour?.map { forecastModel ->
            val timeOfDay = Instant.ofEpochSecond(forecastModel.timeEpoch ?: 0L)
                .atZone(ZoneId.of(tzId))

            ForecastModel.Hour(
                hour = timeOfDay.hour.toString(),
                uv = forecastModel.uv ?: 0.0
            )
        }
    }
}
