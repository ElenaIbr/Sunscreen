package com.example.sunscreen.ui.notifications.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsTimePicker(
    modifier: Modifier = Modifier,
    textColor: Color,
    initialTime: String,
    onClick: (String) -> Unit
) {
    val (hours, min) = initialTime.split(":").map { it.toInt() }
    val time = remember { mutableStateOf(initialTime) }
    val timePickerState = rememberTimePickerState(
        initialHour = hours,
        initialMinute = min
    )
    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = time.value) {
        onClick.invoke(time.value)
    }

    LaunchedEffect(key1 = timePickerState.hour, key2 = timePickerState.minute) {
        val hour = timePickerState.hour.toString()
        val minute = if (timePickerState.minute.toString().length == 1) "0${timePickerState.minute}"
        else timePickerState.minute.toString()
        time.value = "$hour:$minute"
    }

    if (openDialog.value) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = UiColors.background.basePrimary
            ),
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            TimeInput(
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.spacer_16)
                ),
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    periodSelectorBorderColor =  UiColors.mainBrand.primary,
                    periodSelectorSelectedContainerColor =  UiColors.background.baseWhite,
                    periodSelectorUnselectedContainerColor =  UiColors.background.baseWhite,
                    periodSelectorSelectedContentColor =  UiColors.background.baseWhite,
                    periodSelectorUnselectedContentColor =  UiColors.background.baseWhite,
                    timeSelectorSelectedContainerColor =  UiColors.background.baseWhite,
                    timeSelectorUnselectedContainerColor =  UiColors.background.baseWhite,
                    timeSelectorSelectedContentColor =  UiColors.textContent.primary,
                    timeSelectorUnselectedContentColor =  UiColors.textContent.disabled
                )
            )
        }
    }
    Row(
        modifier = modifier.clickable { openDialog.value = true },
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            color = textColor,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h4,
            text = time.value,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_16))
        )
        if (!timePickerState.is24hour) {
            Text(
                color = textColor,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h4,
                text = if (timePickerState.hour >= 12) "PM" else "AM"
            )
        }
    }
}
