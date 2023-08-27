package com.example.remote.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.domain.models.InternetConnectivityEntity
import com.example.domain.services.InternetConnectivityService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class InternetConnectivityServiceImpl(
    private val context: Context
): InternetConnectivityService {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observeInternetConnectivity(): Flow<InternetConnectivityEntity> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(InternetConnectivityEntity.Available) }
                }
                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(InternetConnectivityEntity.Unavailable) }
                }
            }
            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

    override fun isInternetAvailable(): Boolean {
        return connectivityManager.activeNetwork != null
    }
}
