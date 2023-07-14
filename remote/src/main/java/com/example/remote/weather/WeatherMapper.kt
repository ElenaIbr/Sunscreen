package com.example.remote.weather

import com.example.domain.models.IndexModel
import com.example.remote.base.RemoteConverter
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.UUID
import javax.inject.Inject


class WeatherMapper @Inject constructor(
) : RemoteConverter<WeatherResponse, IndexModel> {
    override fun convertFromRemote(remoteModel: WeatherResponse): IndexModel {
        val instant = Instant.ofEpochSecond(remoteModel.location?.localtimeEpoch ?: Instant.now().toEpochMilli()).atZone(ZoneId.of(remoteModel.location?.tzId)).toInstant()

        return IndexModel(
            id = UUID.randomUUID(),
            value = remoteModel.current?.uv,
            date = instant.truncatedTo(ChronoUnit.DAYS),
            temperature = remoteModel.current?.temp,
            location = remoteModel.location?.name,
            coordinates = "${remoteModel.location?.lat}${remoteModel.location?.lon}"
        )
    }
}
