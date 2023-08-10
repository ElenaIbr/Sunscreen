package com.example.sunscreen.ui.components.banner

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.sunscreen.R

enum class BannerValue(
    @ColorRes val contentColor: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val iconColor: Int,
    @DrawableRes val icon: Int,
    @StringRes val text: Int
) {
    High(
        contentColor = R.color.banner_high_level_content_color,
        backgroundColor = R.color.banner_high_level_background_color,
        iconColor = R.color.banner_high_level_icon_color,
        icon = R.drawable.sun_skinny,
        text = R.string.very_high_level_message
    ),
    Medium(
        contentColor = R.color.banner_medium_level_content_color,
        backgroundColor = R.color.banner_medium_level_background_color,
        iconColor = R.color.banner_medium_level_icon_color,
        icon = R.drawable.ic_partly_cloudy,
        text = R.string.moderate_level_message
    ),
    Low(
        contentColor = R.color.banner_low_level_content_color,
        backgroundColor = R.color.banner_low_level_background_color,
        iconColor = R.color.banner_low_level_icon_color,
        icon = R.drawable.ic_cloud,
        text = R.string.low_level_message
    );
}
