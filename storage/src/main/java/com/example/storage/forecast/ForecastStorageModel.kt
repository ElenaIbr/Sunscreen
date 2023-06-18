package com.example.storage.forecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.ForecastModel
import java.util.UUID

@Entity(tableName = "forecast_table")
data class ForecastStorageModel (
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "date") val date: Long?,
    @ColumnInfo(name = "forecast") val forecast: List<ForecastModel.Hour>?
)
