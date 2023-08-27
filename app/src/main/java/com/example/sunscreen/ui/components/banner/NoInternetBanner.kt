package com.example.sunscreen.ui.components.banner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NoInternetBanner(

) {
    val showBanner = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) { showBanner.value = true }

    AnimatedVisibility(
        visible = showBanner.value,
        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Card(
            backgroundColor = Color.Transparent,
            elevation = dimensionResource(id = R.dimen.banner_elevation)
        ) {
            Column (
                modifier = Modifier
                    .background(UiColors.background.disable)
                    .padding(
                        dimensionResource(id = R.dimen.spacer_16)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.no_internet_icon_size)),
                    painter = painterResource(id = R.drawable.ic_wifi_off),
                    tint = UiColors.icons.disabled.copy(alpha = 0.2F),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_16))
                )
                Text(
                    text = stringResource(id = R.string.no_internet_title),
                    color = UiColors.textContent.primary,
                    style = MaterialTheme.typography.h6
                )
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_4))
                )
                Text(
                    text = stringResource(id = R.string.no_internet_content),
                    color = UiColors.textContent.primary,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
