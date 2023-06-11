package com.example.sunscreen.ui

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun CheckPermissions(
    permissions: Array<String>,
    onGetResult: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        onGetResult.invoke(
            permissionsMap.values.reduce { acc, next -> acc && next }
        )
    }

    LaunchedEffect(key1 = Unit) {
        if (
            permissions.all { ContextCompat.checkSelfPermission(context, it) ==
                        PackageManager.PERMISSION_GRANTED }
        ) onGetResult.invoke(true)
        else launcherMultiplePermissions.launch(permissions)
    }
}
