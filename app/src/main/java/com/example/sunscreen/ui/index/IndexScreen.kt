package com.example.sunscreen.ui.index

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sunscreen.R
import com.example.sunscreen.extensions.toStringDate
import com.example.sunscreen.ui.components.Chart
import com.example.sunscreen.ui.components.Loader
import com.example.sunscreen.ui.components.banner.Banner
import com.example.sunscreen.ui.components.banner.BannerValue
import com.example.sunscreen.ui.index.location.GetLocation
import com.example.sunscreen.ui.index.viewmodel.IndexViewModel
import com.example.sunscreen.ui.index.viewmodel.SolarActivity
import com.example.sunscreen.ui.theme.UiColors
import java.time.Instant

@Composable
fun IndexScreen() {
    val viewModel: IndexViewModel = hiltViewModel()
    val indexState by viewModel.indexState.collectAsState()

    /*OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (mainState.latitude != null && mainState.longitude != null) {
                    viewModel.fetchUvValue()
                }
            }
            else -> {}
        }
    }*/

    LaunchedEffect(
        key1 = indexState.latitude,
        key2 = indexState.longitude
    ) {
        if (indexState.latitude != null && indexState.longitude != null) {
            viewModel.fetchUvValue()
            viewModel.fetchForecast()
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

    val backgroundGradientColor = when (indexState.solarActivityLevel) {
        SolarActivity.Low -> {
            UiColors.solarActivityLevel.low.background
        }
        SolarActivity.Medium -> {
            UiColors.solarActivityLevel.medium.background
        }
        SolarActivity.High -> {
            UiColors.solarActivityLevel.high.background
        }
        SolarActivity.VeryHigh -> {
            UiColors.solarActivityLevel.veryHigh.background
        }
        else -> listOf(
            Color.Transparent,
            Color.Transparent
        )
    }

    val textColor = when (indexState.solarActivityLevel) {
        SolarActivity.Low ->  UiColors.solarActivityLevel.low.textColor
        SolarActivity.Medium -> UiColors.solarActivityLevel.medium.textColor
        SolarActivity.High -> UiColors.solarActivityLevel.high.textColor
        SolarActivity.VeryHigh -> UiColors.solarActivityLevel.veryHigh.textColor
        else -> UiColors.textContent.primary
    }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(colors = backgroundGradientColor)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F)
                .padding(
                    top = dimensionResource(id = R.dimen.index_screen_top_padding),
                    start = dimensionResource(id = R.dimen.index_screen_start_padding)
                )
        ) {
            indexState.index?.location?.let { location ->
                Text(
                    text = location,
                    style = MaterialTheme.typography.h6,
                    color = UiColors.textContent.secondary,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = Instant.now().toStringDate(),
                style = MaterialTheme.typography.body1,
                color = UiColors.textContent.secondary,
                textAlign = TextAlign.Center
            )
            indexState.index?.temperature?.let { temperature ->
                Text(
                    text = "${temperature.toInt()}°C",
                    style = MaterialTheme.typography.h4,
                    color = UiColors.textContent.secondary,
                    textAlign = TextAlign.Center
                )
            }
        }
        Banner(
            modifier = Modifier.weight(0.2F),
            when (indexState.solarActivityLevel) {
                SolarActivity.Low -> BannerValue.Low
                SolarActivity.Medium -> BannerValue.Medium
                SolarActivity.High -> BannerValue.High
                SolarActivity.VeryHigh -> BannerValue.High
                else ->  BannerValue.Low
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.index_screen_chart_padding))
                .weight(0.6F),
            contentAlignment = Alignment.Center
        ) {
            Chart(
                forecast = indexState.forecast ?: emptyList(),
                textColor = textColor,
                activity = indexState.solarActivityLevel,
                currentValue = indexState.forecast?.first()?.uv
            )
        }
    }
    if (indexState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(UiColors.background.baseWhite),
            contentAlignment = Alignment.Center
        ) {
            Loader()
        }
    }
}
