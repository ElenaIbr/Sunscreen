package com.example.sunscreen.ui.theme.colors.solarActivity

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R

abstract class Medium {
    abstract val textColor: Color
        @Composable get
    abstract val background: List<Color>
        @Composable get
}

object MediumLight : Medium() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_medium_uv_color)
    override val background: List<Color> @Composable get() = listOf(
        Color.White,
        colorResource(id = R.color.background_low_uv_top),
        colorResource(id = R.color.background_low_uv_bottom),
        Color.White
    )
}

object MediumDark : Medium() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_medium_uv_color)
    override val background: List<Color> @Composable get() = listOf(
        Color.White,
        colorResource(id = R.color.background_low_uv_top),
        colorResource(id = R.color.background_low_uv_bottom),
        Color.White
    )
}
