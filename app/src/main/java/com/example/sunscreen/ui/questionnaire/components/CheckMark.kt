package com.example.sunscreen.ui.questionnaire.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.sunscreen.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CheckMark(checked: Boolean) {
    AnimatedContent(
        targetState = checked,
        transitionSpec = {
            scaleIn() with scaleOut()
        }
    ) { isVisible ->
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    if (isVisible) colorResource(id = R.color.color_primary_light) else Color.White
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.check_mark_padding)),
                painter = painterResource(id = R.drawable.ic_done),
                tint = if (isVisible) Color.Transparent
                else colorResource(id = R.color.banner_high_level_content_color),
                contentDescription = null
            )
        }
    }
}
