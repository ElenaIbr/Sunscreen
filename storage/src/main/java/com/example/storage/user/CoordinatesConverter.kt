package com.example.storage.user

import androidx.room.TypeConverter
import com.example.domain.models.Coordinates
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CoordinatesConverter {
    @TypeConverter
    fun fromCoordinatesToString(cameras: Coordinates?): String? {
        if (cameras == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Coordinates?>() {}.type
        return gson.toJson(cameras, type)
    }

    @TypeConverter
    fun fromStringToCoordinates(cameraString: String?): Coordinates? {
        if (cameraString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Coordinates?>() {}.type
        return gson.fromJson<Coordinates>(cameraString, type)
    }
}
