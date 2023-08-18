package com.example.remote.weather

import com.example.domain.models.ForecastModel
import com.example.remote.base.RemoteConverter
import com.example.remote.forecast.ForecastResponse
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class ForecastMapper @Inject constructor(
) : RemoteConverter<ForecastResponse, ForecastModel> {
    override fun convertFromRemote(remoteModel: ForecastResponse): ForecastModel {
        return ForecastModel(
            date = remoteModel.result?.first()?.uvTime?.toInstant()?.truncatedTo(ChronoUnit.DAYS),
            forecast = remoteModel.result?.map { response ->
                ForecastModel.Hour(
                    hour = response.uvTime?.toInstant()?.atZone(ZoneOffset.UTC)?.hour.toString(),
                    uv = response.uv ?: 0.0
                )
            }
        )
    }
}

fun String.toInstant(): Instant? {
    return try {
        ZonedDateTime.parse(this).toInstant()
    } catch (e: Exception) {
        null
    }
}
