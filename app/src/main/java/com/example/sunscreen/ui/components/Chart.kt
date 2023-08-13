package com.example.sunscreen.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.ForecastModel
import com.example.sunscreen.R
import com.example.sunscreen.ui.index.viewmodel.SolarActivity
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun Chart(
    forecast: List<ForecastModel.Hour>,
    textColor: Color,
    activity: SolarActivity? = SolarActivity.Low,
    currentValue: Double?
) {
    val chartBarGraphHeight = dimensionResource(id = R.dimen.chart_bar_graph_height)
    val chartBarGraphWidth = dimensionResource(id = R.dimen.chart_bar_graph_width)
    val barGraphHeight by remember { mutableStateOf(chartBarGraphHeight) }
    val barGraphWidth by remember { mutableStateOf(chartBarGraphWidth) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.chart_corner)))
            .background(Color.Black.copy(alpha = 0.1F))
            .fillMaxSize()
            .padding(bottom = barGraphHeight / 2),
        contentAlignment = Alignment.Center
    ) {
        if (activity != SolarActivity.Low) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(
                        dimensionResource(id = R.dimen.chart_sun_icon)
                    ),
                painter = painterResource(id = R.drawable.ic_sun_chart),
                tint = UiColors.mainBrand.primary,
                contentDescription = null
            )
        }
        currentValue?.let { value ->
            ChartCircularProgressBar(
                percentage = value.toFloat(),
                color = UiColors.mainBrand.primary
            )
        }
    }
    Row {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    end = dimensionResource(id = R.dimen.chart_graph_end_padding)
                )
                .horizontalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barGraphHeight),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                forecast.forEach { hour ->
                    Box(
                        modifier = Modifier
                            .padding(
                                start = barGraphWidth,
                                bottom = dimensionResource(id = R.dimen.chart_bar_bottom_padding)
                            )
                            .width(barGraphWidth)
                            .clip(
                                RoundedCornerShape(
                                    topStart = dimensionResource(id = R.dimen.chart_bar_corner),
                                    topEnd = dimensionResource(id = R.dimen.chart_bar_corner)
                                )
                            )
                            .fillMaxHeight((hour.uv / 10).toFloat())
                            .background(
                                when (getSolarActivityLevel(hour.uv.toString())) {
                                    SolarActivity.Low -> {
                                        colorResource(id = R.color.chart_low).copy(alpha = 0.3F)
                                    }

                                    SolarActivity.Medium -> {
                                        colorResource(id = R.color.chart_medium).copy(alpha = 0.3F)
                                    }

                                    SolarActivity.High -> {
                                        colorResource(id = R.color.chart_high).copy(alpha = 0.3F)
                                    }

                                    SolarActivity.VeryHigh -> {
                                        colorResource(id = R.color.chart_very_high).copy(alpha = 0.3F)
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .width(barGraphWidth)
                                .height(dimensionResource(id = R.dimen.chart_bar_text_height)),
                            text = hour.uv.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            color = textColor
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        start = barGraphWidth,
                        bottom = dimensionResource(id = R.dimen.chart_text_bottom_padding)
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
            ) {
                forecast.forEach { hour ->
                    Text(
                        modifier = Modifier.width(barGraphWidth),
                        text = "${hour.hour}h",
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = UiColors.textContent.primary
                    )
                }
            }
        }
    }
}

fun getSolarActivityLevel(uvValue: String): SolarActivity {
    return when (uvValue.toDouble()) {
        in 0.0..2.0 -> SolarActivity.Low
        in 3.0..5.0 -> SolarActivity.Medium
        in 6.0..7.0 -> SolarActivity.High
        in 8.0..10.00 -> SolarActivity.VeryHigh
        else -> SolarActivity.VeryHigh
    }
}


@Composable
fun ChartCircularProgressBar(
    percentage: Float,
    radius: Dp = dimensionResource(id = R.dimen.chart_progress_bar_default_radius),
    color: Color = UiColors.mainBrand.primary,
    strokeWidth: Dp = dimensionResource(id = R.dimen.chart_progress_bar_default_stroke),
    animDuration: Int = 3000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) (percentage / 10) else 0F,
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
        androidx.compose.material3.Text(
            text = "${stringResource(id = R.string.uv_index)}$percentage",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            color = UiColors.textContent.secondary
        )
    }
}
