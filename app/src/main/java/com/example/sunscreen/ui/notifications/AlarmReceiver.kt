package com.example.sunscreen.ui.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.sunscreen.MainActivity
import com.example.sunscreen.R

class AlarmReceiver : BroadcastReceiver() {

    /**
     * sends notification when receives alarm
     * and then reschedule the reminder again
     * */
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("sdfdsf", "recieve")
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
            applicationContext = context,
            channelId = context.getString(R.string.reminders_notification_channel_id)
        )
        // Remove this line if you don't want to reschedule the reminder
        RemindersManager.startReminder(context.applicationContext, "8:00", 0)
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle("1212121")
        .setContentText("rerere")
        .setSmallIcon(R.drawable.ic_sun_1)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("sfsdfsdfsf")
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

const val NOTIFICATION_ID = 1
