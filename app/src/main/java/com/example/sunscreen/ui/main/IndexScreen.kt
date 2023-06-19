package com.example.sunscreen.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.domain.models.UvValueModel
import com.example.sunscreen.R
import com.example.sunscreen.ui.GetLocation
import com.example.sunscreen.ui.components.Banner
import com.example.sunscreen.ui.components.Chart
import com.example.sunscreen.ui.components.UvBannerValues

@Composable
fun IndexScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val mainState by viewModel.mainState.collectAsState()

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (mainState.latitude != null && mainState.longitude != null) {
                    viewModel.fetchUvValue()
                }
            }
            else -> {}
        }
    }

    LaunchedEffect(
        key1 = mainState.latitude,
        key2 = mainState.longitude
    ) {
        if (mainState.latitude != null && mainState.longitude != null) {
            viewModel.fetchUvValue()
            viewModel.getForecast()
        }
    }

    GetLocation(
        locationDetails = { location ->
            viewModel.setCoordinates(
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    )

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

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundGradientColor
                )
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
        Banner(
            modifier = Modifier.weight(0.2F),
            when (mainState.solarActivityLevel) {
                UvValueModel.SolarActivityLevel.Low -> UvBannerValues.Low
                UvValueModel.SolarActivityLevel.Medium -> UvBannerValues.Medium
                UvValueModel.SolarActivityLevel.High -> UvBannerValues.High
                UvValueModel.SolarActivityLevel.VeryHigh -> UvBannerValues.High
                else ->  UvBannerValues.Low
            }
        )
        /*Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .weight(0.6F),
            contentAlignment = Alignment.Center
        ) {
            Chart(
                forecast = mainState.index?.forecast?.drop(6) ?: emptyList(),
                textColor = textColor,
                activity = mainState.solarActivityLevel,
                currentValue = mainState.index?.value
            )
        }*/
    }
    if (mainState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = 64.dp),
                color = colorResource(id = R.color.secondary_color).copy(alpha = 0.5F),
                strokeWidth = 6.dp
            )
        }
    }
}
