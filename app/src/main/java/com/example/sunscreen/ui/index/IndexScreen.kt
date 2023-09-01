package com.example.sunscreen.ui.index

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.domain.models.SolarActivity
import com.example.sunscreen.R
import com.example.sunscreen.extensions.toStringDate
import com.example.sunscreen.ui.components.Chart
import com.example.sunscreen.ui.components.Loader
import com.example.sunscreen.ui.components.banner.Banner
import com.example.sunscreen.ui.components.banner.BannerValue
import com.example.sunscreen.ui.components.banner.NoInternetBanner
import com.example.sunscreen.ui.index.location.GetLocation
import com.example.sunscreen.ui.index.viewmodel.IndexViewModel
import com.example.sunscreen.ui.index.viewmodel.ObserveInternetConnectivity
import com.example.sunscreen.ui.index.viewmodel.SetCoordinates
import com.example.sunscreen.ui.index.viewmodel.UpdateLocation
import com.example.sunscreen.ui.theme.UiColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.io.IOException
import java.util.Locale

@Composable
fun IndexScreen() {
    val viewModel: IndexViewModel = hiltViewModel()
    val indexState by viewModel.indexState.collectAsState()

    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(
        key1 = indexState.latitude,
        key2 = indexState.longitude
    ) {
        if (indexState.latitude != null && indexState.longitude != null) {
            viewModel.sendEvent(ObserveInternetConnectivity())
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
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.sendEvent(UpdateLocation())
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val low = UiColors.solarActivityLevel.low.background
    val medium = UiColors.solarActivityLevel.medium.background
    val high = UiColors.solarActivityLevel.high.background
    val veryHigh = UiColors.solarActivityLevel.veryHigh.background
    val unknown = UiColors.background.baseWhite

    val background = remember { mutableStateOf(unknown) }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = background.value,
            darkIcons = true
        )
    }

    LaunchedEffect(
        key1 = indexState.solarActivityLevel,
        key2 = indexState.isLoading
    ) {
        if (indexState.isLoading) {
            background.value = unknown
        } else {
            indexState.solarActivityLevel?.let { activity ->
                background.value = when (activity) {
                    SolarActivity.Low -> low
                    SolarActivity.Medium -> medium
                    SolarActivity.High -> high
                    SolarActivity.VeryHigh -> veryHigh
                }
            }
        }
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
            .background(background.value)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.index_screen_top_padding)
            )
        )
        // Greeting
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.index_screen_horizontal_padding),
                    vertical = dimensionResource(id = R.dimen.index_screen_greeting_padding)
                ),
            text = indexState.userName?.let { name ->
                "${stringResource(id = R.string.hello)}, $name!"
            } ?:  "${stringResource(id = R.string.hello)}!",
            style = MaterialTheme.typography.h5,
            color = UiColors.textContent.secondary,
            textAlign = TextAlign.Start
        )
        // Current location
        if (indexState.latitude != null && indexState.longitude != null) {
            Row(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.index_screen_horizontal_padding),
                    vertical = dimensionResource(id = R.dimen.index_screen_greeting_padding)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.location_icon_size))
                        .clickable {
                            viewModel.sendEvent(UpdateLocation())
                        },
                    painter = painterResource(id = R.drawable.ic_location),
                    tint = UiColors.icons.primary,
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_10))
                )
                getAddress(indexState.latitude ?: 0.0, indexState.longitude ?: 0.0, context)?.let { location ->
                    Text(
                        text = location,
                        style = MaterialTheme.typography.subtitle2,
                        color = UiColors.textContent.note,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
        // Current Uv index
        if (indexState.isInternetAvailable) {
            indexState.index?.value?.let { value ->
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.index_screen_greeting_padding))
                ) {
                    Banner(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.banner_padding)),
                        uvValue = when (indexState.solarActivityLevel) {
                            SolarActivity.Low -> BannerValue.Low
                            SolarActivity.Medium -> BannerValue.Medium
                            SolarActivity.High -> BannerValue.High
                            SolarActivity.VeryHigh -> BannerValue.VeryHigh
                            else ->  BannerValue.Low
                        },
                        uvIndex = value
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.spacer_16)
                    ),
                contentAlignment = Alignment.Center
            ) {
                NoInternetBanner()
            }
        }
        indexState.forecast?.let { forecast ->
            val text = indexState.forecast?.date?.toStringDate() ?: stringResource(id = R.string.today)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.index_screen_horizontal_padding),
                        vertical = dimensionResource(id = R.dimen.index_screen_greeting_padding)
                    ),
                text = "${stringResource(id = R.string.uv_index_forecast_for)} $text:",
                style = MaterialTheme.typography.body1,
                color = UiColors.textContent.primary,
                textAlign = TextAlign.Start
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.index_screen_chart_padding)),
                contentAlignment = Alignment.Center
            ) {
                Chart(
                    isInternetAvailable = indexState.isInternetAvailable,
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
        "${obj.thoroughfare}, ${obj.postalCode} ${obj.subAdminArea}, ${obj.countryName}"
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
