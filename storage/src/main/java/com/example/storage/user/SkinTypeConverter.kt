package com.example.storage.user

import androidx.room.TypeConverter
import com.example.domain.models.UserModel

class SkinTypeConverter {
    @TypeConverter fun stringToEnum(value: String?): UserModel.SkinType {
        value?.let { stringValue -> return enumValueOf(stringValue) } ?: run { return UserModel.SkinType.Unknown }
    }
    @TypeConverter fun enumToString(value: UserModel.SkinType?): String? {
        return value?.name
    }
}
