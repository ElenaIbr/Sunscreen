package com.example.sunscreen.ui.index

import android.content.Intent
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R
import com.example.sunscreen.ui.components.buttons.ButtonState
import com.example.sunscreen.ui.components.buttons.PrimaryButton
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun PermissionsDeniedScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .size(220.dp),
            painter = painterResource(id = R.drawable.ic_sun_chart),
            tint = UiColors.textContent.disabled,
            contentDescription = null
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.access_denied),
            style = MaterialTheme.typography.h5,
            color = UiColors.textContent.disabled,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            text = stringResource(id = R.string.access_denied_message),
            style = MaterialTheme.typography.body2,
            color = UiColors.textContent.disabled,
            textAlign = TextAlign.Center
        )
        PrimaryButton(
            modifier = Modifier.padding(horizontal = 48.dp),
            text = stringResource(id = R.string.settings).uppercase(),
            buttonState = ButtonState.DEFAULT,
            onClick = {
                context.startActivity(
                    Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
        )
    }
}
