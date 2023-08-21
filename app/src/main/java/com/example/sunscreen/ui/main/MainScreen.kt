package com.example.sunscreen.ui.main

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.sunscreen.extensions.OnLifecycleEvent
import com.example.sunscreen.navigation.BottomBar
import com.example.sunscreen.ui.index.IndexScreen
import com.example.sunscreen.ui.index.PermissionsDeniedScreen

@Composable
fun MainScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val locationPermissionsGranted = remember {
        mutableStateOf(false)
    }
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { _ -> }

    BackHandler() {
        (context as Activity).finish()
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE, Lifecycle.Event.ON_RESUME -> {
                if (permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }) {
                    locationPermissionsGranted.value = true
                } else {
                    locationPermissionsGranted.value = false
                    launcherMultiplePermissions.launch(permissions)
                }
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            if (locationPermissionsGranted.value) {
                IndexScreen()
            } else PermissionsDeniedScreen()
        }
    }
}
