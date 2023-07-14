package com.example.storage.index

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IndexDao {

    companion object {
        const val table = "uv_index_table"
    }

    @Query("SELECT * FROM $table")
    fun getAll(): Flow<List<IndexStorageModel>>

    @Query("SELECT * FROM $table ORDER BY date DESC LIMIT 1")
    fun getLastValueInFlow(): Flow<IndexStorageModel?>

    @Query("SELECT * FROM $table LIMIT 1")
    suspend fun getLastValue(): IndexStorageModel?

    @Query("SELECT * FROM $table WHERE date = :currentDate")
    fun getCurrentDayValue(currentDate: String): Flow<List<IndexStorageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addValue(indexStorageModel: IndexStorageModel)

    @Update
    suspend fun updateValue(indexStorageModel: IndexStorageModel)

    @Delete
    suspend fun deleteValue(indexStorageModel: IndexStorageModel)

    @Query("DELETE FROM $table")
    suspend fun clear()
}
