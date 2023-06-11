package com.example.storage.index

import androidx.room.TypeConverter
import com.example.domain.models.IndexModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ForecastConverter {
    @TypeConverter
    fun fromStreamStorageList(streams: List<IndexModel.Hour?>?): String? {
        if (streams == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<IndexModel.Hour?>?>() {}.type
        return gson.toJson(streams, type)
    }

    @TypeConverter
    fun toStreamStorageList(streamString: String?): List<IndexModel.Hour>? {
        if (streamString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<IndexModel.Hour?>?>() {}.type
        return gson.fromJson<List<IndexModel.Hour>>(streamString, type)
    }
}
