package com.example.sunscreen.ui.theme.colors.solarActivity

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

abstract class High {
    abstract val textColor: Color
        @Composable get
    abstract val background: List<Color>
        @Composable get
}

object HighLight : High() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_high_uv_color)
    override val background: List<Color> @Composable get() = listOf(
        UiColors.background.baseWhite,
        colorResource(id = R.color.background_high_uv_top),
        colorResource(id = R.color.background_high_uv_bottom),
        UiColors.background.baseWhite
    )
}

object HighDark : High() {
    override val textColor: Color @Composable get() = colorResource(id = R.color.text_high_uv_color)
    override val background: List<Color> @Composable get() = listOf(
        UiColors.background.baseWhite,
        colorResource(id = R.color.background_high_uv_top),
        colorResource(id = R.color.background_high_uv_bottom),
        UiColors.background.baseWhite
    )
}
