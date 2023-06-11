package com.example.sunscreen.ui.main

import android.Manifest
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.models.UvValueModel
import com.example.sunscreen.R
import com.example.sunscreen.navigation.BottomBar
import com.example.sunscreen.ui.CheckPermissions
import com.example.sunscreen.ui.GetLocation
import com.example.sunscreen.ui.components.Banner
import com.example.sunscreen.ui.components.Chart
import com.example.sunscreen.ui.components.UvBannerValues
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(
    navController: NavController
) {
    val viewModel: MainViewModel = hiltViewModel()
    val mainState by viewModel.mainState.collectAsState()

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }


    LaunchedEffect(
        key1 = mainState.latitude,
        key2 = mainState.longitude
    ) {
        if (mainState.latitude != null && mainState.longitude != null) {
            viewModel.fetchUvValue()
        }
    }

    CheckPermissions(
        permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        onGetResult = { result ->
            viewModel.setPermissionsState(result)
        }
    )

    if (mainState.locationPermissionsGranted) {
        GetLocation(
            locationDetails = { location ->
                viewModel.setCoordinates(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
        )
    }

    val backgroundGradientColor = when (mainState.solarActivityLevel) {
        UvValueModel.SolarActivityLevel.Low -> {
            listOf(
                Color.White,
                colorResource(id = R.color.background_low_uv_top),
                colorResource(id = R.color.background_low_uv_bottom),
                Color.White
            )
        }
        UvValueModel.SolarActivityLevel.Medium -> {
            listOf(
                Color.White,
                colorResource(id = R.color.background_medium_uv_top),
                colorResource(id = R.color.background_medium_uv_bottom),
                Color.White
            )
        }
        UvValueModel.SolarActivityLevel.High -> {
            listOf(
                Color.White,
                colorResource(id = R.color.background_high_uv_top),
                colorResource(id = R.color.background_high_uv_bottom),
                Color.White
            )
        }
        UvValueModel.SolarActivityLevel.VeryHigh -> {
            listOf(
                Color.White,
                colorResource(id = R.color.background_very_high_uv_top),
                colorResource(id = R.color.background_very_high_uv_bottom),
                Color.White
            )
        }
        else -> listOf(
            Color.Transparent,
            Color.Transparent
        )
    }

    val textColor = when (mainState.solarActivityLevel) {
        UvValueModel.SolarActivityLevel.Low -> colorResource(id = R.color.text_medium_uv_color)
        UvValueModel.SolarActivityLevel.Medium -> colorResource(id = R.color.text_medium_uv_color)
        UvValueModel.SolarActivityLevel.High -> colorResource(id = R.color.text_high_uv_color)
        UvValueModel.SolarActivityLevel.VeryHigh -> colorResource(id = R.color.text_very_high_uv_color)
        else -> colorResource(id = R.color.text_very_high_uv_color)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddings ->
        if (mainState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(size = 64.dp),
                    color = colorResource(id = R.color.primary_button_color),
                    strokeWidth = 6.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = backgroundGradientColor
                        )
                    )
                    .padding(
                        bottom = paddings.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2F)
                        .padding(
                            top = 24.dp,
                            start = 48.dp
                        )
                ) {
                    mainState.index?.location?.let { location ->
                        Text(
                            text = location,
                            style = MaterialTheme.typography.h6,
                            color = textColor,
                            textAlign = TextAlign.Center
                        )
                    }
                    mainState.date?.let { date ->
                        Text(
                            text = date,
                            style = MaterialTheme.typography.body1,
                            color = textColor,
                            textAlign = TextAlign.Center
                        )
                    }
                    mainState.index?.temperature?.let { temperature ->
                        Text(
                            text = "${temperature.toInt()}°C",
                            style = MaterialTheme.typography.h6,
                            color = textColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(0.3F)
                ) {
                    Banner(
                        when (mainState.solarActivityLevel) {
                            UvValueModel.SolarActivityLevel.Low -> UvBannerValues.Low
                            UvValueModel.SolarActivityLevel.Medium -> UvBannerValues.Medium
                            UvValueModel.SolarActivityLevel.High -> UvBannerValues.High
                            UvValueModel.SolarActivityLevel.VeryHigh -> UvBannerValues.High
                            else ->  UvBannerValues.Low
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .weight(0.5F),
                    contentAlignment = Alignment.Center
                ) {
                    Chart(
                        forecast = mainState.index?.forecast?.drop(6) ?: emptyList(),
                        textColor = textColor,
                        activity = mainState.solarActivityLevel,
                        currentValue = mainState.index?.value.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    radius: Dp = 50.dp,
    color: Color = colorResource(id = R.color.color_primary_light),
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 3000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0F,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    val gradient = listOf(
        colorResource(id = R.color.color_gradient_7),
        colorResource(id = R.color.color_gradient_6),
        colorResource(id = R.color.color_gradient_5),
        colorResource(id = R.color.color_gradient_4),
        colorResource(id = R.color.color_gradient_3),
        Color.Transparent
    )

    LaunchedEffect(key1 = Unit) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier.size(radius * 2F),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(radius * 2F)
        ) {
            drawCircle(
                brush = Brush.radialGradient(gradient),
                radius = radius.toPx()
            )

            drawArc(
                color = Color.LightGray,
                -90F,
                360F,
                false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = color,
                -90F,
                360 * currentPercentage.value,
                false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = percentage.toString(),
            style = MaterialTheme.typography.h5,
            color = colorResource(id = R.color.color_secondary_dark)
        )
    }
}

