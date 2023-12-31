package com.example.sunscreen.ui.theme.colors.solarActivity

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

abstract class VeryHigh {
    abstract val textColor: Color
        @Composable get
    abstract val backgroundGradient: List<Color>
        @Composable get
    abstract val background: Color
        @Composable get
}

object VeryHighLight : VeryHigh() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_very_high_uv_color)
    override val backgroundGradient: List<Color> @Composable get() = listOf(
        colorResource(id = R.color.background_very_high),
        colorResource(id = R.color.background_very_high_uv_top),
        colorResource(id = R.color.background_very_high_uv_bottom),
        colorResource(id = R.color.background_very_high)
    )
    override val background: Color @Composable get() = colorResource(id = R.color.background_very_high)
}

object VeryHighDark : VeryHigh() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_very_high_uv_color)
    override val backgroundGradient: List<Color> @Composable get() = listOf(
        colorResource(id = R.color.background_very_high),
        colorResource(id = R.color.background_very_high_uv_top),
        colorResource(id = R.color.background_very_high_uv_bottom),
        colorResource(id = R.color.background_very_high)
    )
    override val background: Color @Composable get() = colorResource(id = R.color.background_very_high)
}
