package com.example.sunscreen.ui.questionnaire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun QuestionItem(
    value: String,
    isSelected: Boolean = false,
    onItemClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.question_item_corners))
            )
            .fillMaxWidth()
            .background(
                color = if (isSelected) Color.Transparent
                else UiColors.mainBrand.primary
            )
            .clickable {
                onItemClick.invoke(!isSelected)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.question_item_padding),
                top = dimensionResource(id = R.dimen.question_item_padding),
                bottom = dimensionResource(id = R.dimen.question_item_padding)
            )
        ) {
            CheckMark(isSelected)
        }
        Spacer(
            modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_24))
        )
        Text(
            color = UiColors.textContent.primary,
            text = value,
            textAlign = TextAlign.Start,
            fontSize = 16.sp

        )
    }
}
