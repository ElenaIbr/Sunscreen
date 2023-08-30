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
import com.example.domain.models.UserModel
import com.example.domain.services.NotificationsManager
import com.example.domain.usecases.CheckIfInternetAvailableUseCase
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
class NotificationsReceiver : BroadcastReceiver() {
    @Inject
    lateinit var fetchForecastForNotification: FetchForecastForNotification
    @Inject
    lateinit var getUserSingleUseCase: GetUserSingleUseCase
    @Inject
    lateinit var checkIfInternetAvailableUseCase: CheckIfInternetAvailableUseCase
    @Inject
    lateinit var notificationsManager: NotificationsManager

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

            val user = getUserSingleUseCase.execute(Unit).data

            if (checkIfInternetAvailableUseCase.execute(Unit).data == true) {
                when(
                    val result = fetchForecastForNotification.execute(Unit)
                ) {
                    is Resource.Success -> {
                        result.successData?.let { solarActivity ->
                            notificationManager.createNotification(
                                applicationContext = context,
                                channelId = context.getString(R.string.reminders_notification_channel_id),
                                user = user,
                                solarActivity = solarActivity
                            )
                        }
                        user?.let { userModel ->
                            startReminder(userModel)
                        }
                        cancelJob()
                    }
                    is Resource.Error -> {
                        user?.let { userModel ->
                            startReminder(userModel)
                        }
                        cancelJob()
                    }
                }
            } else {
                user?.let { userModel ->
                    startReminder(userModel)
                }
                cancelJob()
            }
        }
    }
    private fun NotificationManager.createNotification(
        applicationContext: Context,
        channelId: String,
        user: UserModel?,
        solarActivity: SolarActivity?
    ) {
        getNotificationBody(user ,solarActivity)?.let { notificationBody ->
            val contentIntent = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                1,
                contentIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val builder = NotificationCompat.Builder(applicationContext, channelId)
                .setContentTitle(applicationContext.getString(R.string.app_name))
                .setContentText(
                    applicationContext.getString(notificationBody.message)
                )
                .setSmallIcon(notificationBody.image)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            notify(NOTIFICATION_ID, builder.build())
        }
    }
    private fun startReminder(userModel: UserModel) {
        userModel.notifications?.let { notification ->
            if (notification.notificationEnabled) {
                notificationsManager.startNotifications(
                    notification.start
                )
            }
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
    user: UserModel?,
    solarActivity: SolarActivity?
): ForecastNotificationBody? {
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
                message = when (user?.skinColor ?: UserModel.SkinColor.Unknown) {
                    UserModel.SkinColor.Fair -> R.string.spf_type_fair_moderate_notification_message
                    UserModel.SkinColor.Pale -> R.string.spf_type_pale_moderate_notification_message
                    UserModel.SkinColor.Medium -> R.string.spf_type_medium_moderate_notification_message
                    UserModel.SkinColor.Olive -> R.string.spf_type_olive_moderate_notification_message
                    UserModel.SkinColor.Brown -> R.string.spf_type_brown_moderate_notification_message
                    UserModel.SkinColor.Dark -> R.string.spf_type_dark_moderate_notification_message
                    UserModel.SkinColor.Unknown -> R.string.medium_level_notification_message
                }
            )
        }
        SolarActivity.High -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = when (user?.skinColor ?: UserModel.SkinColor.Unknown) {
                    UserModel.SkinColor.Fair -> R.string.spf_type_fair_high_notification_message
                    UserModel.SkinColor.Pale -> R.string.spf_type_pale_high_notification_message
                    UserModel.SkinColor.Medium -> R.string.spf_type_medium_high_notification_message
                    UserModel.SkinColor.Olive -> R.string.spf_type_olive_high_notification_message
                    UserModel.SkinColor.Brown -> R.string.spf_type_brown_high_notification_message
                    UserModel.SkinColor.Dark -> R.string.spf_type_dark_high_notification_message
                    UserModel.SkinColor.Unknown -> R.string.high_level_notification_message
                }
            )
        }
        SolarActivity.VeryHigh -> {
            ForecastNotificationBody(
                image = R.drawable.ic_sun_chart,
                message = when (user?.skinColor ?: UserModel.SkinColor.Unknown) {
                    UserModel.SkinColor.Fair -> R.string.spf_type_fair_very_high_notification_message
                    UserModel.SkinColor.Pale -> R.string.spf_type_pale_very_high_notification_message
                    UserModel.SkinColor.Medium -> R.string.spf_type_medium_very_high_notification_message
                    UserModel.SkinColor.Olive -> R.string.spf_type_olive_very_high_notification_message
                    UserModel.SkinColor.Brown -> R.string.spf_type_brown_very_high_notification_message
                    UserModel.SkinColor.Dark -> R.string.spf_type_dark_very_high_notification_message
                    UserModel.SkinColor.Unknown -> R.string.very_high_level_notification_message
                }
            )
        }
        else -> null
    }
}
