package com.example.domain.services

import com.example.domain.models.InternetConnectivityEntity
import kotlinx.coroutines.flow.Flow

interface InternetConnectivityService {
    fun observeInternetConnectivity(): Flow<InternetConnectivityEntity>
    fun isInternetAvailable(): Boolean
}
