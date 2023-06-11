package com.example.storage.user

import androidx.room.TypeConverter
import com.example.domain.models.Notification
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class NotificationsConverter {
    @TypeConverter
    fun fromNotificationToString(cameras: Notification?): String? {
        if (cameras == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Notification?>() {}.type
        return gson.toJson(cameras, type)
    }

    @TypeConverter
    fun fromStringToNotification(cameraString: String?): Notification? {
        if (cameraString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Notification?>() {}.type
        return gson.fromJson<Notification>(cameraString, type)
    }
}
