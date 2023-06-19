package com.example.storage.forecast

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    companion object {
        const val table = "forecast_table"
    }

    @Query("SELECT * FROM $table")
    fun getAllInFlow(): Flow<List<ForecastStorageModel>>

    @Query("SELECT * FROM $table")
    suspend fun getAll(): List<ForecastStorageModel>

    @Query("SELECT * FROM $table ORDER BY date DESC LIMIT 1")
    fun getFirstValue(): Flow<ForecastStorageModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addValue(indexStorageModel: ForecastStorageModel)

    @Update
    suspend fun updateValue(indexStorageModel: ForecastStorageModel)

    @Delete
    suspend fun deleteValue(indexStorageModel: ForecastStorageModel)

    @Query("DELETE FROM $table")
    suspend fun clear()
}
