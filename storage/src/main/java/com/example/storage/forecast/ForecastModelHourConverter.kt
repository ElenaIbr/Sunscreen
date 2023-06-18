package com.example.storage.forecast

import androidx.room.TypeConverter
import com.example.domain.models.ForecastModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ForecastModelHourConverter {
    @TypeConverter
    fun fromStreamStorageList(streams: List<ForecastModel.Hour?>?): String? {
        if (streams == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastModel.Hour?>?>() {}.type
        return gson.toJson(streams, type)
    }

    @TypeConverter
    fun toStreamStorageList(streamString: String?): List<ForecastModel.Hour>? {
        if (streamString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ForecastModel.Hour?>?>() {}.type
        return gson.fromJson<List<ForecastModel.Hour>>(streamString, type)
    }
}
