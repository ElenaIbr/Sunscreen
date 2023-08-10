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
import com.example.domain.models.ForecastModel
import com.example.domain.models.UvValueModel
import com.example.domain.usecases.GetForecastByDateUseCase
import com.example.domain.usecases.GetUserEntity
import com.example.domain.usecases.GetUserUseCase
import com.example.sunscreen.MainActivity
import com.example.sunscreen.R
import com.example.sunscreen.ui.components.getSolarActivityLevel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.TimeZone
import javax.inject.Inject

const val NOTIFICATION_ID = 1

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var getForecastByDateUseCase: GetForecastByDateUseCase
    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            val currentDate = Instant.now()
                .atZone(TimeZone.getDefault().toZoneId())
                .toInstant()
                .truncatedTo(ChronoUnit.DAYS)
            getForecastByDateUseCase.execute(currentDate).collect { forecastList ->
                val notificationManager = ContextCompat.getSystemService(
                    context,
                    NotificationManager::class.java
                ) as NotificationManager

                notificationManager.sendReminderNotification(
                    applicationContext = context,
                    channelId = context.getString(R.string.reminders_notification_channel_id),
                    forecastList = forecastList
                )
            }
            getUserUseCase.execute(Unit).collect { flow ->
                when(flow) {
                    is GetUserEntity.Success -> {
                        RemindersManager.startReminder(context.applicationContext, flow.userModel?.notifications?.start ?: "8:00", 0)
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
        forecastList: List<ForecastModel.Hour>?,
    ) {
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        forecastList?.maxOfOrNull { it.uv }?.let { index ->
            val forecastNotificationBody = getNotificationBody(index.toString())
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
    indexValue: String
): ForecastNotificationBody {
    return when (
        getSolarActivityLevel(indexValue)
    ) {
        UvValueModel.SolarActivityLevel.Low -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
        UvValueModel.SolarActivityLevel.Medium -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
        UvValueModel.SolarActivityLevel.High -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
        UvValueModel.SolarActivityLevel.VeryHigh -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = R.string.low_level_notification_message
            )
        }
    }
}
