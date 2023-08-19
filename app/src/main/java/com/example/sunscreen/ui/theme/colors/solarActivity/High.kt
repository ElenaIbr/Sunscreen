package com.example.sunscreen.ui.theme.colors.solarActivity

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

abstract class High {
    abstract val textColor: Color
        @Composable get
    abstract val backgroundGradient: List<Color>
        @Composable get
    abstract val background: Color
        @Composable get
}

object HighLight : High() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_high_uv_color)
    override val backgroundGradient: List<Color> @Composable get() = listOf(
        colorResource(id = R.color.background_high),
        colorResource(id = R.color.background_high_uv_top),
        colorResource(id = R.color.background_high_uv_bottom),
        colorResource(id = R.color.background_high)
    )
    override val background: Color @Composable get() = colorResource(id = R.color.background_high)
}

object HighDark : High() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_high_uv_color)
    override val backgroundGradient: List<Color> @Composable get() = listOf(
        colorResource(id = R.color.background_high),
        colorResource(id = R.color.background_high_uv_top),
        colorResource(id = R.color.background_high_uv_bottom),
        colorResource(id = R.color.background_high)
    )
    override val background: Color @Composable get() = colorResource(id = R.color.background_high)
}
