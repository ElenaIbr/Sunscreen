package com.example.sunscreen.ui.components.banner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.sunscreen.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    uvValue: BannerValue
) {
    val showBanner = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) { showBanner.value = true }

    AnimatedVisibility(
        visible = showBanner.value,
        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Card(
            modifier = modifier.padding(
                horizontal = dimensionResource(id = R.dimen.banner_horizontal_padding),
                vertical = dimensionResource(id = R.dimen.banner_vertical_padding)
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.banner_corner)),
            backgroundColor = colorResource(id = uvValue.backgroundColor).copy(alpha = 0.5F),
            contentColor = colorResource(id = uvValue.contentColor),
            elevation = dimensionResource(id = R.dimen.banner_elevation)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(id = R.dimen.banner_content_padding)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.banner_icon_size)),
                    painter = painterResource(id = uvValue.icon),
                    tint = colorResource(id = uvValue.iconColor),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.width(
                        dimensionResource(id = R.dimen.spacer_16)
                    )
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
