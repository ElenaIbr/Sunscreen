package com.example.sunscreen.ui.index

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sunscreen.extensions.toStringDate
import com.example.sunscreen.ui.index.location.GetLocation
import com.example.sunscreen.ui.components.Chart
import com.example.sunscreen.ui.components.banner.Banner
import com.example.sunscreen.ui.components.banner.BannerValue
import com.example.sunscreen.ui.index.viewmodel.IndexViewModel
import com.example.sunscreen.ui.index.viewmodel.SolarActivity
import com.example.sunscreen.ui.theme.UiColors
import java.time.Instant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IndexScreen() {
    val viewModel: IndexViewModel = hiltViewModel()
    val mainState by viewModel.indexState.collectAsState()

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
        key1 = mainState.latitude,
        key2 = mainState.longitude
    ) {
        if (mainState.latitude != null && mainState.longitude != null) {
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

    val backgroundGradientColor = when (mainState.solarActivityLevel) {
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

    val textColor = when (mainState.solarActivityLevel) {
        SolarActivity.Low ->  UiColors.solarActivityLevel.low.textColor
        SolarActivity.Medium -> UiColors.solarActivityLevel.medium.textColor
        SolarActivity.High -> UiColors.solarActivityLevel.high.textColor
        SolarActivity.VeryHigh -> UiColors.solarActivityLevel.veryHigh.textColor
        else -> UiColors.textContent.primary
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
            Text(
                text = Instant.now().toStringDate(),
                style = MaterialTheme.typography.body1,
                color = textColor,
                textAlign = TextAlign.Center
            )
            mainState.index?.temperature?.let { temperature ->
                Text(
                    text = "${temperature.toInt()}°C",
                    style = MaterialTheme.typography.h4,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            }
        }
        Banner(
            modifier = Modifier.weight(0.2F),
            when (mainState.solarActivityLevel) {
                SolarActivity.Low -> BannerValue.Low
                SolarActivity.Medium -> BannerValue.Medium
                SolarActivity.High -> BannerValue.High
                SolarActivity.VeryHigh -> BannerValue.High
                else ->  BannerValue.Low
            }
        )
        HorizontalPager(
            pageCount = 0,
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .background(Color.LightGray)
                    .fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .weight(0.6F),
            contentAlignment = Alignment.Center
        ) {
            Chart(
                forecast = mainState.forecast ?: emptyList(),
                textColor = textColor,
                activity = mainState.solarActivityLevel,
                currentValue = mainState.forecast?.first()?.uv
            )
        }
    }
    if (mainState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = 48.dp),
                color = UiColors.mainBrand.primary.copy(alpha = 0.5F),
                strokeWidth = 6.dp
            )
        }
    }
}
