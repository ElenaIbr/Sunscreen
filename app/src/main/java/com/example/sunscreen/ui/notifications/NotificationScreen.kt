package com.example.sunscreen.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sunscreen.R
import com.example.sunscreen.navigation.BottomBar
import com.example.sunscreen.ui.components.TopBar
import com.example.sunscreen.ui.notifications.components.NotificationLayout

@Composable
fun NotificationScreen(
    navController: NavController
) {
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val notificationState by notificationViewModel.notificationState.collectAsState()

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBar(
                onClick = {
                    notificationViewModel.updateNotifications()
                    createNotificationsChannels(context)
                    notificationState.notification?.let {
                        RemindersManager.startReminder(
                            context = context,
                            reminderTime = it.start
                        )
                    }
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
                isEnabled = notificationState.user?.notifications?.notificationEnabled ?: false,
                onSetNotificationTime = { notifications ->
                    notificationViewModel.setNotifications(notifications)
                }
            )
        }
    }
}

private fun createNotificationsChannels(context: Context) {
    val channel = NotificationChannel(
        context.getString(R.string.reminders_notification_channel_id),
        "name",
        NotificationManager.IMPORTANCE_HIGH
    )
    ContextCompat.getSystemService(context, NotificationManager::class.java)
        ?.createNotificationChannel(channel)
}
