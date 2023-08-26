package com.example.storage.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.Coordinates
import com.example.domain.models.Notification
import com.example.domain.models.UserModel
import java.util.UUID

@Entity(tableName = "user_table")
data class UserStorageModel (
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "birthDate") val birthDate: String,
    @ColumnInfo(name = "skinType") val skinType: UserModel.SkinType,
    @ColumnInfo(name = "skinColor") val skinColor: UserModel.SkinColor,
    @ColumnInfo(name = "coordinates") val coordinates: Coordinates?,
    @ColumnInfo(name = "notifications") val notifications: Notification?
)
