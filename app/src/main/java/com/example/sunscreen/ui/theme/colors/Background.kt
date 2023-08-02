package com.example.sunscreen.ui.theme.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R

abstract class Background {
    abstract val baseWhite: Color
        @Composable get
    abstract val basePrimary: Color
        @Composable get
}

object BackgroundLight : Background() {
    override val baseWhite: Color
        @Composable get() = colorResource(id = R.color.background_base_white)
    override val basePrimary: Color
        @Composable get() = colorResource(id = R.color.background_base_primary)
}

object BackgroundDark : Background() {
    override val baseWhite: Color
        @Composable get() = colorResource(id = R.color.background_base_white)
    override val basePrimary: Color
        @Composable get() = colorResource(id = R.color.background_base_primary)
}
