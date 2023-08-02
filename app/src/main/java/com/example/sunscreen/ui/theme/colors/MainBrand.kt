package com.example.sunscreen.ui.theme.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R

abstract class MainBrand {
    abstract val primary: Color
        @Composable get
    abstract val secondary: Color
        @Composable get
}

object MainBrandLight : MainBrand() {
    override val primary: Color
        @Composable get() = colorResource(id = R.color.main_brand_primary)
    override val secondary: Color
        @Composable get() = colorResource(id = R.color.main_brand_secondary)
}

object MainBrandDark : MainBrand() {
    override val primary: Color
        @Composable get() = colorResource(id = R.color.main_brand_primary)
    override val secondary: Color
        @Composable get() = colorResource(id = R.color.main_brand_secondary)
}
