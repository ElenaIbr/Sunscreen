package com.example.storage.user

import androidx.room.TypeConverter
import com.example.domain.models.UserModel

class SkinColorConverter {
    @TypeConverter
    fun stringToEnum(value: String?): UserModel.SkinColor {
        value?.let { stringValue -> return enumValueOf(stringValue) } ?: run { return UserModel.SkinColor.Unknown }
    }
    @TypeConverter
    fun enumToString(value: UserModel.SkinColor?): String? {
        return value?.name
    }
}
