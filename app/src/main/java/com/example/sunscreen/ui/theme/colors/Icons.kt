package com.example.sunscreen.ui.theme.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R

abstract class Icons {
    abstract val primary: Color
        @Composable get
}

object IconsLight : Icons() {
    override val primary: Color @Composable get() = colorResource(id = R.color.color_icon_primary)
}

object IconsDark : Icons() {
    override val primary: Color @Composable get() = colorResource(id = R.color.color_icon_primary)
}