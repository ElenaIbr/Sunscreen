package com.example.sunscreen.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R
import com.example.sunscreen.ui.components.buttons.ButtonState

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    buttonState: ButtonState? = ButtonState.DEFAULT,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = onClick,
        enabled = buttonState == ButtonState.DEFAULT,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = colorResource(id = R.color.primary_button_color),
            disabledContentColor = colorResource(id = R.color.primary_button_content_disable)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.primary_button_color)
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
