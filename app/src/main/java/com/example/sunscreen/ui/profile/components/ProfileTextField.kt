package com.example.sunscreen.ui.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun ProfileTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = dimensionResource(id = R.dimen.profile_input_field_text_padding),
            ),
        text = title,
        fontWeight = FontWeight.Bold,
        color = if (value.isEmpty()) UiColors.textContent.error else UiColors.textContent.primary,
        style = MaterialTheme.typography.subtitle1
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.profile_input_field_corner))
            )
            .border(
                BorderStroke(
                    width = dimensionResource(id = R.dimen.profile_input_field_border),
                    color = UiColors.textContent.disabled
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.profile_input_field_corner))
            ),
        value = value,
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.profile_input_field_corner)),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = UiColors.textContent.disabled,
            backgroundColor = UiColors.background.baseWhite,
            focusedLabelColor = UiColors.textContent.disabled,
            unfocusedLabelColor = UiColors.textContent.disabled,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            errorCursorColor = UiColors.textContent.error
        ),
        onValueChange = { newValue ->
            onValueChange.invoke(newValue)
        },
        trailingIcon = {
            if (value.isEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error),
                    tint = UiColors.textContent.error,
                    contentDescription = null
                )
            }
        }
    )
    if (value.isEmpty()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.spacer_16),
                    bottom = dimensionResource(id = R.dimen.profile_input_field_text_padding)
                ),
            text = stringResource(id = R.string.this_field_is_required),
            color = UiColors.textContent.error,
            style = MaterialTheme.typography.subtitle2
        )
    }
}
