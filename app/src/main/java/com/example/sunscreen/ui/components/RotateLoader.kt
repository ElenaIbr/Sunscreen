package com.example.sunscreen.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun RotateLoader(
    modifier: Modifier = Modifier,
    color: Color = UiColors.mainBrand.primary,
    size: Dp = dimensionResource(id = R.dimen.rotate_loader_default_size)
) {
    val animation = rememberInfiniteTransition()
    val angle = animation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    Icon(
        painter = painterResource(id = R.drawable.ic_loader),
        contentDescription = null,
        modifier = modifier
            .size(size)
            .rotate(angle.value),
        tint = color
    )
}
