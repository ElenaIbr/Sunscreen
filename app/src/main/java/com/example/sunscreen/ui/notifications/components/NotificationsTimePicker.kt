package com.example.sunscreen.ui.notifications.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R
import java.util.Calendar

@Composable
fun NotificationsTimePicker(
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
            val hour = h.toString()
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
