package com.example.sunscreen.ui.theme.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.sunscreen.R

abstract class TextContent {
    abstract val primary: Color
        @Composable get
    abstract val secondary: Color
        @Composable get
    abstract val error: Color
        @Composable get
    abstract val note: Color
        @Composable get
}

object TextContentLight : TextContent() {
    override val primary: Color
        @Composable get() = colorResource(id = R.color.text_primary)
    override val secondary: Color
        @Composable get() = colorResource(id = R.color.text_secondary)
    override val error: Color
        @Composable get() = colorResource(id = R.color.text_error)
    override val note: Color
        @Composable get() = colorResource(id = R.color.text_note)
}

object TextContentDark : TextContent() {
    override val primary: Color
        @Composable get() = colorResource(id = R.color.text_primary)
    override val secondary: Color
        @Composable get() = colorResource(id = R.color.text_secondary)
    override val error: Color
        @Composable get() = colorResource(id = R.color.text_error)
    override val note: Color
        @Composable get() = colorResource(id = R.color.text_note)
}
