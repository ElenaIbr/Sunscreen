package com.example.storage.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    companion object {
        const val table = "user_table"
    }

    @Query("SELECT * FROM $table LIMIT 1")
    fun getUserFlow(): Flow<UserStorageModel?>

    @Query("SELECT * FROM $table LIMIT 1")
    suspend fun getUser(): UserStorageModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(personStorageModel: UserStorageModel)

    @Update
    suspend fun updateUser(personStorageModel: UserStorageModel)

    @Delete
    suspend fun deleteUser(personStorageModel: UserStorageModel)

    @Query("DELETE from $table")
    suspend fun clear()
}
