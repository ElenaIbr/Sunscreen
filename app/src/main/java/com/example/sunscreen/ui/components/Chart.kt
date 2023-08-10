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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.ForecastModel
import com.example.domain.models.UvValueModel
import com.example.sunscreen.R

@Composable
fun Chart(
    forecast: List<ForecastModel.Hour>,
    textColor: Color,
    maxValue: Int = 10,
    activity: UvValueModel.SolarActivityLevel? = UvValueModel.SolarActivityLevel.Low,
    currentValue: Double?
) {
    val barGraphHeight by remember { mutableStateOf(200.dp) }
    val barGraphWidth by remember { mutableStateOf(24.dp) }
    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black.copy(alpha = 0.1F))
            .fillMaxSize()
            .padding(bottom = barGraphHeight / 2),
        contentAlignment = Alignment.Center
    ) {
        if (activity != UvValueModel.SolarActivityLevel.Low) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(220.dp),
                painter = painterResource(id = R.drawable.ic_sun_1),
                tint = colorResource(id = R.color.color_primary_light),
                contentDescription = null
            )
        }
        currentValue?.let { value ->
            CircularProgressBar(
                radius = 50.dp,
                percentage = value.toFloat()
            )
        }
    }
    Row {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp)
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
                            .padding(start = barGraphWidth, bottom = 1.dp)
                            .width(barGraphWidth)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp
                                )
                            )
                            .fillMaxHeight((hour.uv / 10).toFloat())
                            .background(
                                when (getSolarActivityLevel(hour.uv.toString())) {
                                    UvValueModel.SolarActivityLevel.Low -> {
                                        colorResource(id = R.color.chart_low).copy(alpha = 0.3F)
                                    }

                                    UvValueModel.SolarActivityLevel.Medium -> {
                                        colorResource(id = R.color.chart_medium).copy(alpha = 0.3F)
                                    }

                                    UvValueModel.SolarActivityLevel.High -> {
                                        colorResource(id = R.color.chart_high).copy(alpha = 0.3F)
                                    }

                                    UvValueModel.SolarActivityLevel.VeryHigh -> {
                                        colorResource(id = R.color.chart_very_high).copy(alpha = 0.3F)
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .width(barGraphWidth)
                                .height(16.dp),
                            text = hour.uv.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.background_bottom)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .height(16.dp)
                    .padding(
                        start = barGraphWidth,
                        bottom = 12.dp
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
                        color = textColor
                    )
                }
            }
        }
    }
}

fun getSolarActivityLevel(uvValue: String): UvValueModel.SolarActivityLevel {
    return when (uvValue.toDouble()) {
        in 0.0..2.0 -> UvValueModel.SolarActivityLevel.Low
        in 3.0..5.0 -> UvValueModel.SolarActivityLevel.Medium
        in 6.0..7.0 -> UvValueModel.SolarActivityLevel.High
        in 8.0..10.00 -> UvValueModel.SolarActivityLevel.VeryHigh
        else -> UvValueModel.SolarActivityLevel.VeryHigh
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
            color = colorResource(id = R.color.color_secondary_dark)
        )
    }
}
