package com.example.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.storage.index.ForecastConverter
import com.example.storage.index.IndexDao
import com.example.storage.index.IndexStorageModel
import com.example.storage.user.NotificationsConverter
import com.example.storage.user.SkinColorConverter
import com.example.storage.user.UserDao
import com.example.storage.user.UserStorageModel
import com.example.storage.user.SkinTypeConverter

@Database(
    entities = [
        IndexStorageModel::class,
        UserStorageModel::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(
    SkinTypeConverter::class,
    SkinColorConverter::class,
    ForecastConverter::class,
    NotificationsConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun indexDao(): IndexDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration().build()
        }
    }
}
