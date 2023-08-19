package com.example.sunscreen.ui.theme.colors.solarActivity

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R

abstract class Low {
    abstract val textColor: Color
        @Composable get
    abstract val backgroundGradient: List<Color>
        @Composable get
    abstract val background: Color
        @Composable get
}

object LowLight : Low() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_medium_uv_color)
    override val backgroundGradient: List<Color> @Composable get() = listOf(
        colorResource(id = R.color.background_low),
        colorResource(id = R.color.background_low_uv_top),
        colorResource(id = R.color.background_low_uv_bottom),
        colorResource(id = R.color.background_low)
    )
    override val background: Color @Composable get() = colorResource(id = R.color.background_low)
}

object LowDark : Low() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_medium_uv_color)
    override val backgroundGradient: List<Color> @Composable get() = listOf(
        colorResource(id = R.color.background_low),
        colorResource(id = R.color.background_low_uv_top),
        colorResource(id = R.color.background_low_uv_bottom),
        colorResource(id = R.color.background_low)
    )
    override val background: Color @Composable get() = colorResource(id = R.color.background_low)
}
