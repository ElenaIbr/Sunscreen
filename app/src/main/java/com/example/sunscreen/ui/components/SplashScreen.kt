package com.example.sunscreen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SplashScreen() {
    val systemUiController = rememberSystemUiController()
    val backgroundColor = UiColors.background.baseWhite

    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = backgroundColor,
            darkIcons = true
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(dimensionResource(id = R.dimen.splash_screen_icon_size)),
            painter = painterResource(id = R.drawable.sun_skinny),
            tint = UiColors.mainBrand.primary,
            contentDescription = null
        )
    }
}
