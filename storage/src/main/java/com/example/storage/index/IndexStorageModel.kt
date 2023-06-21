package com.example.storage.index

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "uv_index_table")
data class IndexStorageModel (
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "value") val value: Double?,
    @ColumnInfo(name = "date") val date: Long?,
    @ColumnInfo(name = "temp") val temperature: Double?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "coordinates") val coordinates: String?
)
