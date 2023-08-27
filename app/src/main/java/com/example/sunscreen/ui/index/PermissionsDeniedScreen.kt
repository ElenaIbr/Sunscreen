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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.sunscreen.R
import com.example.sunscreen.ui.components.buttons.ButtonState
import com.example.sunscreen.ui.components.buttons.PrimaryButton
import com.example.sunscreen.ui.theme.UiColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PermissionsDeniedScreen() {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val statusBarColor = UiColors.background.baseWhite

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = true
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(id = R.dimen.permissions_denied_screen_icon_size)),
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
                .padding(dimensionResource(id = R.dimen.permissions_denied_screen_text_padding)),
            text = stringResource(id = R.string.access_denied_message),
            style = MaterialTheme.typography.body2,
            color = UiColors.textContent.disabled,
            textAlign = TextAlign.Center
        )
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.permissions_denied_button_padding)),
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
