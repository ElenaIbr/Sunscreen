package com.example.sunscreen.ui.index

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sunscreen.R
import com.example.sunscreen.extensions.toStringDate
import com.example.sunscreen.ui.components.Chart
import com.example.sunscreen.ui.components.ChartCircularProgressBar
import com.example.sunscreen.ui.components.Loader
import com.example.sunscreen.ui.components.banner.Banner
import com.example.sunscreen.ui.components.banner.BannerValue
import com.example.sunscreen.ui.index.location.GetLocation
import com.example.sunscreen.ui.index.viewmodel.FetchForecast
import com.example.sunscreen.ui.index.viewmodel.FetchUvValue
import com.example.sunscreen.ui.index.viewmodel.IndexViewModel
import com.example.sunscreen.ui.index.viewmodel.SetCoordinates
import com.example.sunscreen.ui.index.viewmodel.SolarActivity
import com.example.sunscreen.ui.theme.UiColors
import java.io.IOException
import java.time.Instant
import java.util.Locale


@Composable
fun IndexScreen() {
    val viewModel: IndexViewModel = hiltViewModel()
    val indexState by viewModel.indexState.collectAsState()

    val context = LocalContext.current

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
            viewModel.sendEvent(FetchUvValue())
            viewModel.sendEvent(FetchForecast())
        }
    }

    GetLocation(
        locationDetails = { location ->
            viewModel.sendEvent(
                SetCoordinates(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
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
                .weight(0.1F)
                .padding(
                    top = dimensionResource(id = R.dimen.index_screen_top_padding),
                    start = dimensionResource(id = R.dimen.index_screen_start_padding)
                )
        ) {
            if (indexState.latitude != null && indexState.longitude != null) {
                getAddress(indexState.latitude ?: 0.0, indexState.longitude ?: 0.0, context)?.let { location ->
                    Text(
                        text = location,
                        style = MaterialTheme.typography.h6,
                        color = UiColors.textContent.secondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        indexState.index?.value?.let { value ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2F),
                contentAlignment = Alignment.Center
            ) {
                ChartCircularProgressBar(
                    percentage = value,
                    color = UiColors.mainBrand.primary
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
                .weight(0.3F),
            contentAlignment = Alignment.Center
        ) {
            indexState.forecast?.let { forecast ->
                Chart(
                    forecast = forecast,
                    textColor = textColor
                )
            }
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


fun getAddress(lat: Double, lng: Double, context: Context): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
        val obj: Address = addresses!![0]

        "${obj.subAdminArea}, ${obj.countryName}"
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
