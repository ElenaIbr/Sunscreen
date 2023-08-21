package com.example.sunscreen.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun PrimarySwitch(
    width: Dp = dimensionResource(id = R.dimen.switch_default_width),
    height: Dp = dimensionResource(id = R.dimen.switch_default_height),
    checkedTrackColor: Color = UiColors.mainBrand.secondary,
    uncheckedTrackColor: Color = UiColors.textContent.disabled,
    trackSize: Dp = dimensionResource(id = R.dimen.switch_track_size),
    initialState: Boolean = false,
    onStateChanged:(Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var switchOn by remember { mutableStateOf(initialState) }
    val alignment by animateAlignmentAsState(if (switchOn) 1f else -1f)

    LaunchedEffect(switchOn) {
        onStateChanged.invoke(switchOn)
    }

    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(
                width = dimensionResource(id = R.dimen.switch_border_width),
                color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                shape = RoundedCornerShape(percent = 50)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { switchOn = !switchOn },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = alignment
        ) {
            Icon(
                modifier = Modifier
                    .size(size = trackSize)
                    .background(
                        color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                        shape = CircleShape
                    )
                    .padding(dimensionResource(id = R.dimen.switch_icon_padding)),
                painter = painterResource(
                    id = if (switchOn) R.drawable.ic_notifications
                    else R.drawable.ic_notifications_disabled
                ),
                tint = if (switchOn) UiColors.icons.secondary else UiColors.icons.primary,
                contentDescription = if (switchOn) "Enabled" else "Disabled"
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}
