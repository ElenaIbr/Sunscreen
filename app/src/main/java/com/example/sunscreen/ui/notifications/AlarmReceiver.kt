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
import com.example.domain.usecases.GetUserEntity
import com.example.domain.usecases.GetUserUseCase
import com.example.sunscreen.MainActivity
import com.example.sunscreen.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

const val NOTIFICATION_ID = 1

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var fetchForecastForNotification: FetchForecastForNotification
    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendReminderNotification(
                applicationContext = context,
                channelId = context.getString(R.string.reminders_notification_channel_id),
                solarActivity = fetchForecastForNotification.execute(getLocalDateTime().toString()).data
            )
            getUserUseCase.execute(Unit).collect { flow ->
                when(flow) {
                    is GetUserEntity.Success -> {
                        RemindersManager.startReminder(context.applicationContext, flow.userModel?.notifications?.start ?: "22:15", 0)
                        cancelJob()
                    }
                    else -> {}
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

fun getLocalDateTime(): LocalDateTime {
    val date = Date()
    val utc: ZonedDateTime = date.toInstant().atZone(ZoneOffset.UTC)
    val default = utc.withZoneSameInstant(ZoneId.systemDefault())
    return default.toLocalDateTime()
}
