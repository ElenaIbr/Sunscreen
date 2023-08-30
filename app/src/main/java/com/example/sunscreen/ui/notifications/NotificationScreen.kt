package com.example.sunscreen.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import com.example.sunscreen.ui.notifications.viewmodel.NotificationViewModel
import com.example.sunscreen.ui.notifications.viewmodel.SetNotifications
import com.example.sunscreen.ui.notifications.viewmodel.UpdateNotifications
import com.example.sunscreen.ui.theme.UiColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NotificationScreen(
    navController: NavController
) {
    val notificationViewModel: NotificationViewModel = hiltViewModel()
    val notificationState by notificationViewModel.notificationState.collectAsState()

    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val statusBarColor = UiColors.background.baseWhite

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = true
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBar(
                enabled = notificationState.notificationWasChanged,
                onClick = {
                    notificationViewModel.sendEvent(UpdateNotifications())
                    if (notificationState.notification?.notificationEnabled == true) {
                        createNotificationsChannels(context)
                        notificationState.notification?.let {
                            RemindersManager.startReminder(
                                context = context,
                                reminderTime = it.start
                            )
                        }
                    } else {
                        RemindersManager.stopReminder(
                            context = context
                        )
                    }
                    Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddings ->
        notificationState.notification?.let { notification ->
            Box(
                modifier = Modifier.padding(paddings)
            ) {
                NotificationLayout(
                    notification = notification,
                    onSetNotificationTime = { notifications ->
                        notificationViewModel.sendEvent(SetNotifications(notifications))
                    }
                )
            }
        }
    }
}

private fun createNotificationsChannels(context: Context) {
    val channel = NotificationChannel(
        context.getString(R.string.reminders_notification_channel_id),
        "notifications",
        NotificationManager.IMPORTANCE_HIGH
    )
    ContextCompat.getSystemService(context, NotificationManager::class.java)
        ?.createNotificationChannel(channel)
}
