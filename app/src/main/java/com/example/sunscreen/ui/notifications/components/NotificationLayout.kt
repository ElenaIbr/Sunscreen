package com.example.sunscreen.ui.notifications.components

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.models.Notification
import com.example.sunscreen.R

@Composable
fun NotificationLayout(
    notification: Notification,
    onSetNotificationTime: (Notification) -> Unit
) {
    val start = remember { mutableStateOf(notification.start) }
    var switchCheckedState by remember { mutableStateOf(notification.notificationEnabled) }

    LaunchedEffect(
        key1 = switchCheckedState,
        key2 = start.value
    ) {
        onSetNotificationTime(
            Notification(
                notificationEnabled = switchCheckedState,
                start = start.value
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
                text = stringResource(id = R.string.enable_notifications),
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
            NotificationsTimePicker(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp
                    ),
                label = stringResource(id = R.string.start),
                initialTime = start.value,
                onClick = {
                    start.value = it
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
