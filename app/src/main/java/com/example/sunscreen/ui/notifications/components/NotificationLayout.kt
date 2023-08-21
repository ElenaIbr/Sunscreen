package com.example.sunscreen.ui.notifications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.domain.models.Notification
import com.example.sunscreen.R
import com.example.sunscreen.ui.components.PrimarySwitch
import com.example.sunscreen.ui.theme.UiColors

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
                    vertical = dimensionResource(id = R.dimen.notification_screen_switch_vertical_padding),
                    horizontal = dimensionResource(id = R.dimen.notification_screen_horizontal_padding)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.enable_notifications),
                fontWeight = FontWeight.Bold,
                color = UiColors.textContent.primary,
                style = MaterialTheme.typography.subtitle1
            )
            PrimarySwitch(
                initialState = switchCheckedState,
                onStateChanged = { value ->
                    switchCheckedState = value
                }
            )
        }
        if (switchCheckedState) {
            Divider(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(id = R.dimen.spacer_16),
                    ),
                color = UiColors.textContent.disabled
            )
            NotificationsTimePicker(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.notification_screen_horizontal_padding)
                    ),
                label = stringResource(id = R.string.start),
                initialTime = start.value,
                onClick = { value ->
                    start.value = value
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.spacer_16),
                        horizontal = dimensionResource(id = R.dimen.spacer_16),
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    tint = UiColors.icons.primary,
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_24))
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.notifications_info),
                    color = UiColors.textContent.primary,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
