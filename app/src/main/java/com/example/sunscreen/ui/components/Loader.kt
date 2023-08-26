package com.example.sunscreen.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun Loader(
    size: Dp = dimensionResource(id = R.dimen.default_loader_size),
    strokeWidth: Dp = dimensionResource(id = R.dimen.default_loader_stroke_width),
    color: Color = UiColors.mainBrand.primary.copy(alpha = 0.5F),
    animationDelay: Int = 1000
) {
    val circleScale = remember {
        mutableStateOf(0f)
    }
    val circleScaleAnimate = animateFloatAsState(
        targetValue = circleScale.value,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        )
    )
    LaunchedEffect(Unit) {
        circleScale.value = 1f
    }
    Box(
        modifier = Modifier
            .size(size)
            .scale(scale = circleScaleAnimate.value)
            .border(
                width = strokeWidth,
                color = color.copy(alpha = 1 - circleScaleAnimate.value),
                shape = CircleShape
            )
    )
}
