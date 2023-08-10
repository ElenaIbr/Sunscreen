package com.example.sunscreen.ui.theme.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R


abstract class Buttons {
    abstract val primary: Color
        @Composable get
    abstract val secondary: Color
        @Composable get
    abstract val primaryContent: Color
        @Composable get
    abstract val secondaryContent: Color
        @Composable get
}

object ButtonsLight : Buttons() {
    override val primary: Color @Composable get() = colorResource(id = R.color.color_button_primary)
    override val secondary: Color @Composable get() = colorResource(id = R.color.color_button_secondary)
    override val primaryContent: Color @Composable get() = colorResource(id = R.color.primary_button_content)
    override val secondaryContent: Color @Composable get() = colorResource(id = R.color.color_button_secondary)
}

object ButtonsDark : Buttons() {
    override val primary: Color @Composable get() = colorResource(id = R.color.color_button_primary)
    override val secondary: Color @Composable get() = colorResource(id = R.color.color_button_secondary)
    override val primaryContent: Color @Composable get() = colorResource(id = R.color.primary_button_content)
    override val secondaryContent: Color @Composable get() = colorResource(id = R.color.color_button_secondary)
}
