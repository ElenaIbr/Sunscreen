package com.example.sunscreen.ui.notifications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.spacer_16),
                horizontal = dimensionResource(id = R.dimen.spacer_16),
            )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (switchCheckedState) UiColors.background.basePrimary
                else UiColors.background.disable.copy(alpha = 0.5F)
            )
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_16))
            ) {
                NotificationsTimePicker(
                    textColor = if (switchCheckedState) UiColors.textContent.primary
                    else UiColors.textContent.disabled,
                    initialTime = start.value,
                    onClick = { value ->
                        start.value = value
                    }
                )
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_10))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (switchCheckedState) stringResource(id = R.string.notifications_enabled)
                        else stringResource(id = R.string.notifications_disabled),
                        color = if (switchCheckedState) UiColors.textContent.primary
                        else UiColors.textContent.disabled,
                        style = MaterialTheme.typography.body2
                    )
                    PrimarySwitch(
                        initialState = switchCheckedState,
                        onStateChanged = { value ->
                            switchCheckedState = value
                        }
                    )
                }
            }
        }
        if (switchCheckedState) {
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_16))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    tint = UiColors.icons.primary,
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_16))
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
