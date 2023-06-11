package com.example.storage.index

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.IndexModel
import java.time.Instant
import java.util.UUID

@Entity(tableName = "uv_index_table")
data class IndexStorageModel (
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "value") val value: Double?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "forecast") val forecast: List<IndexModel.Hour>?
)
