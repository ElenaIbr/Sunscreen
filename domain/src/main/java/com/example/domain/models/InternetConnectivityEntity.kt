package com.example.domain.models

sealed class InternetConnectivityEntity {
    object Available: InternetConnectivityEntity()
    object Unavailable: InternetConnectivityEntity()
}
