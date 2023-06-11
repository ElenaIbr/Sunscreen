package com.example.sunscreen.ui.notifications.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.sunscreen.R
import com.example.sunscreen.ui.notifications.RemindersManager
import com.example.sunscreen.ui.notifications.models.Notification
import java.util.Calendar

@Composable
fun NotificationLayout(
    onSetNotificationTime: (Notification) -> Unit
) {
    val context = LocalContext.current
    val hour = remember { mutableStateOf(8) }
    val minute = remember { mutableStateOf(0) }
    val days = remember { mutableListOf<DayOfWeek>() }

    var switchCheckedState by remember { mutableStateOf(false) }

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val mTimePickerDialog = TimePickerDialog(
        context,
        {_, h : Int, m: Int ->
            hour.value = h
            minute.value = m
        }, mHour, mMinute, DateFormat.is24HourFormat(context)
    )

    LaunchedEffect(
        key1 = switchCheckedState,
        key2 = hour.value,
        key3 = minute.value
    ) {
        onSetNotificationTime(
            Notification(
                notificationEnabled = switchCheckedState,
                notificationHour = hour.value,
                notificationMinute = minute.value,
                days = days
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                color = colorResource(id = R.color.primary_text_color),
                text = "Enable notifications",
                style = MaterialTheme.typography.h6
            )
            Switch(
                checked = switchCheckedState,
                onCheckedChange = {
                    switchCheckedState = it
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.primary_color),
                    checkedTrackColor = colorResource(id = R.color.primary_background),
                    checkedBorderColor = colorResource(id = R.color.primary_color),
                    uncheckedThumbColor = colorResource(id = R.color.primary_button_disable),
                    uncheckedTrackColor = colorResource(id = R.color.primary_background),
                    uncheckedBorderColor = colorResource(id = R.color.primary_button_disable),
                    disabledCheckedThumbColor = colorResource(id = R.color.primary_color).copy(alpha = ContentAlpha.disabled),
                    disabledCheckedTrackColor = colorResource(id = R.color.primary_background).copy(alpha = ContentAlpha.disabled),
                    disabledCheckedBorderColor = colorResource(id = R.color.primary_color).copy(alpha = ContentAlpha.disabled),
                    disabledUncheckedThumbColor = colorResource(id = R.color.primary_button_disable).copy(alpha = ContentAlpha.disabled),
                    disabledUncheckedTrackColor = colorResource(id = R.color.primary_background).copy(alpha = ContentAlpha.disabled),
                    disabledUncheckedBorderColor = colorResource(id = R.color.primary_button_disable).copy(alpha = ContentAlpha.disabled),
                )
            )
        }
        if (switchCheckedState) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        mTimePickerDialog.show()
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            mTimePickerDialog.show()
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = if (hour.value.toString().length == 1) "0${hour.value}"
                        else hour.value.toString(),
                        color = colorResource(id = R.color.primary_text_color),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = ":",
                        color = colorResource(id = R.color.primary_text_color),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = if (minute.value.toString().length == 1) "0${minute.value}"
                        else minute.value.toString(),
                        color = colorResource(id = R.color.primary_text_color),
                        style = MaterialTheme.typography.h3
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DayOfWeek.values().forEach { day ->
                    item {
                        NotificationDay(
                            dayOfWeek = day,
                            onClick = { isEnabled ->
                                if (isEnabled) days.add(day) else days.remove(day)
                            }
                        )
                    }
                }
            }

            val id = stringResource(id = R.string.reminders_notification_channel_id)
            val name = stringResource(id = R.string.reminders_notification_channel_name)
            Button(onClick = {
                createNotificationsChannels(
                    context = context,
                    id,
                    name
                )
                RemindersManager.startReminder(
                    context = context
                )
            }) {
                Text(text = "efewf")
            }
        }
    }
}

@Composable
fun NotificationDay(
    dayOfWeek: DayOfWeek,
    onClick: (Boolean) -> Unit
) {
    val isEnabled = remember { mutableStateOf(true) }

    Button(
        modifier= Modifier
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = if (isEnabled.value) colorResource(id = R.color.primary_color)
                    else colorResource(id = R.color.primary_button_disable)
                ),
                shape = CircleShape
            )
            .clip(CircleShape)
            .size(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled.value) Color.White else colorResource(id = R.color.primary_color),
            contentColor = if (isEnabled.value) colorResource(id = R.color.primary_color) else Color.White
        ),
        shape = CircleShape,
        onClick = {
            isEnabled.value = !isEnabled.value
            onClick.invoke(!isEnabled.value)
        }
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = DayOfWeek.fromValue(dayOfWeek),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.overline.copy(
                fontSize = 12.sp
            )
        )
    }
}

enum class DayOfWeek(val day: String) {
    Monday("M"),
    Tuesday("T"),
    Wednesday("W"),
    Thursday("T"),
    Friday("F"),
    Saturday("S"),
    Sunday("S");

    companion object {
        fun fromValue(dayOfWeek: DayOfWeek): String = values().find { it == dayOfWeek }?.day ?: ""
    }
}

private fun createNotificationsChannels(context: Context, id: String, name: String) {
    val channel = NotificationChannel(
        id,
        name,
        NotificationManager.IMPORTANCE_HIGH
    )
    ContextCompat.getSystemService(context, NotificationManager::class.java)
        ?.createNotificationChannel(channel)
}
