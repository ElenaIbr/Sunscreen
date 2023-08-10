package com.example.sunscreen.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R
import com.example.sunscreen.ui.components.RotateLoader
import com.example.sunscreen.ui.theme.UiColors

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
            backgroundColor = UiColors.buttons.primary,
            contentColor = UiColors.buttons.primaryContent
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
                    tint = UiColors.buttons.primaryContent,
                    contentDescription = null
                )
            }
        }
    }
}
