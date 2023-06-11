package com.example.sunscreen.ui.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Banner(
    uvValue: UvBannerValues
) {
    val showBanner = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) { showBanner.value = true }

    AnimatedVisibility(
        visible = showBanner.value,
        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Card(
            modifier = Modifier.padding(24.dp),
            shape = RoundedCornerShape(4.dp),
            backgroundColor = colorResource(id = uvValue.backgroundColor).copy(alpha = 0.5F),
            contentColor = colorResource(id = uvValue.contentColor),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = uvValue.icon),
                    tint = colorResource(id = uvValue.iconColor),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                Text(
                    text = stringResource(id = uvValue.text),
                    color = colorResource(id = uvValue.contentColor),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

enum class UvBannerValues(
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
        icon = R.drawable.ic_sunny,
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
