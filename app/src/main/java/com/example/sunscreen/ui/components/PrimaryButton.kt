package com.example.sunscreen.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    buttonState: ButtonState? = ButtonState.DEFAULT,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = onClick,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 2.dp,
            disabledElevation = 2.dp
        ),
        enabled = buttonState == ButtonState.DEFAULT,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.primary_button_color),
            contentColor = colorResource(id = R.color.primary_button_content)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        when (buttonState) {
            ButtonState.DEFAULT, ButtonState.DISABLED, null -> {
                Text(
                    text = text,
                    fontWeight = FontWeight(600),
                    lineHeight = TextUnit(24f, TextUnitType.Sp),
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                )
            }
            ButtonState.LOADING -> {
                RotateLoader(modifier = Modifier.align(Alignment.CenterVertically))
            }
            ButtonState.SUCCESS -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_done),
                    tint = colorResource(id = R.color.primary_button_content),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun RotateLoader(
    modifier: Modifier = Modifier,
    color: Color = colorResource(id = R.color.primary_button_content),
    size: Dp = 28.dp
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
