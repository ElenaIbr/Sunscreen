package com.example.sunscreen.ui.main

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.UvValueModel
import com.example.sunscreen.R
import com.example.sunscreen.extensions.toStringDate
import com.example.sunscreen.ui.GetLocation
import com.example.sunscreen.ui.components.Chart
import com.example.sunscreen.ui.components.banner.Banner
import com.example.sunscreen.ui.components.banner.BannerValue
import com.example.sunscreen.ui.index.viewmodel.IndexViewModel
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
        UvValueModel.SolarActivityLevel.Low -> {
            listOf(
                UiColors.background.baseWhite,
                colorResource(id = R.color.background_low_uv_top),
                colorResource(id = R.color.background_low_uv_bottom),
                Color.White
            )
        }
        UvValueModel.SolarActivityLevel.Medium -> {
            listOf(
                UiColors.background.baseWhite,
                colorResource(id = R.color.background_medium_uv_top),
                colorResource(id = R.color.background_medium_uv_bottom),
                UiColors.background.baseWhite
            )
        }
        UvValueModel.SolarActivityLevel.High -> {
            listOf(
                UiColors.background.baseWhite,
                colorResource(id = R.color.background_high_uv_top),
                colorResource(id = R.color.background_high_uv_bottom),
                UiColors.background.baseWhite
            )
        }
        UvValueModel.SolarActivityLevel.VeryHigh -> {
            listOf(
                UiColors.background.baseWhite,
                colorResource(id = R.color.background_very_high_uv_top),
                colorResource(id = R.color.background_very_high_uv_bottom),
                UiColors.background.baseWhite
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
                    start = 12.dp
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
                    style = MaterialTheme.typography.h5,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            }
        }
        Banner(
            modifier = Modifier.weight(0.2F),
            when (mainState.solarActivityLevel) {
                UvValueModel.SolarActivityLevel.Low -> BannerValue.Low
                UvValueModel.SolarActivityLevel.Medium -> BannerValue.Medium
                UvValueModel.SolarActivityLevel.High -> BannerValue.High
                UvValueModel.SolarActivityLevel.VeryHigh -> BannerValue.High
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
                modifier = Modifier.size(size = 64.dp),
                color = colorResource(id = R.color.secondary_color).copy(alpha = 0.5F),
                strokeWidth = 6.dp
            )
        }
    }
}
