package com.example.sunscreen.ui.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.domain.models.SolarActivity
import com.example.domain.usecases.FetchForecastForNotification
import com.example.domain.usecases.GetUserSingleUseCase
import com.example.domain.utils.Resource
import com.example.sunscreen.MainActivity
import com.example.sunscreen.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NOTIFICATION_ID = 1

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var fetchForecastForNotification: FetchForecastForNotification
    @Inject
    lateinit var getUserSingleUseCase: GetUserSingleUseCase

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

            when(
                val result = fetchForecastForNotification.execute(Unit)
            ) {
                is Resource.Success -> {
                    result.successData?.let { solarActivity ->
                        notificationManager.sendReminderNotification(
                            applicationContext = context,
                            channelId = context.getString(R.string.reminders_notification_channel_id),
                            solarActivity = solarActivity
                        )
                    }
                    RemindersManager.startReminder(
                        context.applicationContext,
                        getUserSingleUseCase.execute(Unit).data?.notifications?.start ?: "08:00",
                        0
                    )
                    cancelJob()
                }
                is Resource.Error -> {
                    RemindersManager.startReminder(
                        context.applicationContext,
                        getUserSingleUseCase.execute(Unit).data?.notifications?.start ?: "08:00",
                        0
                    )
                    cancelJob()
                }
            }
        }
    }
    private fun NotificationManager.sendReminderNotification(
        applicationContext: Context,
        channelId: String,
        solarActivity: SolarActivity?
    ) {
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val forecastNotificationBody = getNotificationBody(solarActivity)
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(
                applicationContext.getString(forecastNotificationBody.message)
            )
            .setSmallIcon(forecastNotificationBody.image)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        notify(NOTIFICATION_ID, builder.build())
    }
    private fun cancelJob() {
        job.cancel()
    }
}

data class ForecastNotificationBody(
    @DrawableRes val image: Int,
    @StringRes val message: Int
)

fun getNotificationBody(
    solarActivity: SolarActivity?
): ForecastNotificationBody {
    return when (solarActivity) {
        SolarActivity.Low -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
        SolarActivity.Medium -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
        SolarActivity.High -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
        SolarActivity.VeryHigh -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
        else -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
    }
}
