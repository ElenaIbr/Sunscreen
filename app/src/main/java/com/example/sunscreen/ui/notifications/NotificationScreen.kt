package com.example.sunscreen.ui.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.sunscreen.navigation.BottomBar
import com.example.sunscreen.ui.components.TopBar
import com.example.sunscreen.ui.notifications.components.NotificationLayout

@Composable
fun NotificationScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBar(
                onClick = {

                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddings ->
        Box(
            modifier = Modifier.padding(paddings)
        ) {
            NotificationLayout(
                onSetNotificationTime = {}
            )
        }
    }
}
