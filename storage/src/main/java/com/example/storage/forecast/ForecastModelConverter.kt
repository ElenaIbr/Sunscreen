package com.example.storage.forecast

import androidx.room.TypeConverter
import com.example.domain.models.ForecastDay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ForecastModelConverter {
    @TypeConverter
    fun fromStreamStorageList(streams: List<ForecastDay?>?): String? {
        if (streams == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastDay?>?>() {}.type
        return gson.toJson(streams, type)
    }

    @TypeConverter
    fun toStreamStorageList(streamString: String?): List<ForecastDay>? {
        if (streamString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastDay.Hour?>?>() {}.type
        return gson.fromJson<List<ForecastDay>>(streamString, type)
    }
}
