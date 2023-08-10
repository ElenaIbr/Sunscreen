package com.example.sunscreen.di

import android.app.Application
import com.example.storage.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }
    @Singleton
    @Provides
    fun provideIndexDao(appDatabase: AppDatabase) = appDatabase.indexDao()

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
}
