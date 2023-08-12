package com.example.sunscreen.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun Loader(
    size: Dp = dimensionResource(id = R.dimen.default_loader_size),
    strokeWidth: Dp = dimensionResource(id = R.dimen.default_loader_stroke_width)
) {
    CircularProgressIndicator(
        modifier = Modifier.size(size = size),
        color = UiColors.mainBrand.primary.copy(alpha = 0.5F),
        strokeWidth = strokeWidth
    )
}
