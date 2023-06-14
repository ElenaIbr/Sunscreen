package com.example.sunscreen.ui.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sunscreen.navigation.BottomBar
import com.example.sunscreen.ui.components.TopBar
import com.example.sunscreen.ui.notifications.components.NotificationLayout

@Composable
fun NotificationScreen(
    navController: NavController
) {
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val notificationState by notificationViewModel.notificationState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBar(
                onClick = {
                    notificationViewModel.updateNotifications()
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
                onSetNotificationTime = { notifications ->
                    notificationViewModel.setNotifications(notifications)
                }
            )
        }
    }
}
