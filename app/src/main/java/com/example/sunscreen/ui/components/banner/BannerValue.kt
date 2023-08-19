package com.example.sunscreen.ui.components.banner

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.sunscreen.R

enum class BannerValue(
    @ColorRes val contentColor: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val progressColor: Int,
    @StringRes val text: Int
) {
    VeryHigh(
        contentColor = R.color.banner_high_level_content_color,
        backgroundColor = R.color.banner_high_level_background_color,
        progressColor = R.color.banner_very_high_level_progress_color,
        text = R.string.very_high_level_message
    ),
    High(
        contentColor = R.color.banner_high_level_content_color,
        backgroundColor = R.color.banner_high_level_background_color,
        progressColor = R.color.banner_high_level_progress_color,
        text = R.string.high_level_message
    ),
    Medium(
        contentColor = R.color.banner_medium_level_content_color,
        backgroundColor = R.color.banner_medium_level_background_color,
        progressColor = R.color.banner_medium_level_progress_color,
        text = R.string.moderate_level_message
    ),
    Low(
        contentColor = R.color.banner_low_level_content_color,
        backgroundColor = R.color.banner_low_level_background_color,
        progressColor = R.color.banner_low_level_progress_color,
        text = R.string.low_level_message
    );
}
