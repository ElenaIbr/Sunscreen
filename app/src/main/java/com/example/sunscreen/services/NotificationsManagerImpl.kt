package com.example.sunscreen.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.domain.services.NotificationsManager
import com.example.sunscreen.ui.notifications.NotificationsReceiver
import java.util.Calendar
import java.util.Locale

private const val REMINDER_NOTIFICATION_REQUEST_CODE = 123

class NotificationsManagerImpl(
    private val context: Context
): NotificationsManager {
    override fun startNotifications(
        reminderTime: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val (hours, minutes) = reminderTime.split(":").map { it.toInt() }
        val intent =
            Intent(context.applicationContext, NotificationsReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(
                    context.applicationContext,
                    REMINDER_NOTIFICATION_REQUEST_CODE,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        val calendar: Calendar = Calendar.getInstance(Locale.ENGLISH).apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
        }
        if (Calendar.getInstance(Locale.ENGLISH)
                .apply { add(Calendar.MINUTE, 1) }.timeInMillis - calendar.timeInMillis > 0
        ) {
            calendar.add(Calendar.DATE, 1)
        }
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, intent),
            intent
        )
    }
    override fun stopNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationsReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                REMINDER_NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}
