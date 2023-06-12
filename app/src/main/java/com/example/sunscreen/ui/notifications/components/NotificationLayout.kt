package com.example.sunscreen.ui.notifications.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.domain.models.Notification
import com.example.sunscreen.R
import java.util.Calendar

@Composable
fun NotificationLayout(
    onSetNotificationTime: (Notification) -> Unit
) {
    val context = LocalContext.current
    val start = remember { mutableStateOf("") }
    val end = remember { mutableStateOf("") }

    var switchCheckedState by remember { mutableStateOf(false) }

    LaunchedEffect(
        key1 = switchCheckedState,
        key2 = start,
        key3 = end
    ) {
        onSetNotificationTime(
            Notification(
                notificationEnabled = switchCheckedState,
                start = start.value,
                end = end.value
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp,
                    horizontal = 24.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = "Enable notifications",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = MaterialTheme.typography.subtitle1
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
            Divider(
                modifier = Modifier
                    .padding(
                        vertical = 16.dp,
                    ),
                color = colorResource(id = R.color.divider_color)
            )
            NotificationsTime(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp
                    ),
                label = "Start",
                initialTime = "08:00",
                onClick = {
                    start.value = it
                }
            )
            NotificationsTime(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp
                    ),
                label = "End",
                initialTime = "21:00",
                onClick = {
                    end.value = it
                }
            )
            Divider(
                modifier = Modifier
                    .padding(
                        vertical = 16.dp,
                    ),
                color = colorResource(id = R.color.divider_color)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    tint = Color.Black,
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_24))
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.notifications_info),
                    color = Color.Black,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun NotificationsTime(
    modifier: Modifier = Modifier,
    label: String,
    initialTime: String,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    val time = remember { mutableStateOf(initialTime) }

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val timePickerDialog = TimePickerDialog(
        context,
        {_, h : Int, m: Int ->
            val hour = if (h.toString().length == 1) "0$h" else h.toString()
            val minute =  if (m.toString().length == 1) "0$m" else m.toString()
            time.value = "$hour:$minute"
        }, mHour, mMinute, true
    )

    LaunchedEffect(key1 = time.value) {
        onClick.invoke(time.value)
    }

    Row(
        modifier = modifier
            .clickable {
                timePickerDialog.show()
            }
            .padding(
                vertical = 8.dp
            )
    ) {
        Text(
            color = Color.Black,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1,
            text = label,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_16))
        )
        Text(
            color = Color.Black,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1,
            text = time.value
        )
    }
}
