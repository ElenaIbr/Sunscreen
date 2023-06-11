package com.example.domain.models

import java.util.UUID

data class UserModel(
    val id: UUID,
    val name: String,
    val birthDate: String,
    val skinType: SkinType,
    val skinColor: SkinColor,
    val notifications: Notification
) {
    enum class SkinType{
        Normal,
        Sensitive,
        Dry,
        Oily,
        Combination,
        Unknown;

        companion object {
            fun fromValue(value: String): SkinType = values().find {
                it.name == value
            } ?: Unknown
        }
    }
    enum class SkinColor{
        Pale,
        Fair,
        Medium,
        Olive,
        Brown,
        Dark,
        Unknown;

        companion object {
            fun fromValue(value: String): SkinColor = values().find {
                it.name == value
            } ?: Unknown
        }
    }
}
