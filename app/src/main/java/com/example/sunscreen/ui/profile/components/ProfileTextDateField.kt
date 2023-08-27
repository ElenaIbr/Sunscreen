package com.example.sunscreen.ui.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun ProfileTextDateField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val maxChar = 4

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = dimensionResource(id = R.dimen.profile_input_field_text_padding)
            ),
        text = title,
        fontWeight = FontWeight.Bold,
        color = if (value.length < 4) UiColors.textContent.error else UiColors.textContent.primary,
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
                RoundedCornerShape(dimensionResource(id = R.dimen.profile_input_field_corner))
            ),
        value = value,
        isError = value.length < 4,
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.profile_input_field_corner)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = UiColors.background.baseWhite,
            focusedLabelColor = UiColors.textContent.disabled,
            unfocusedLabelColor = UiColors.textContent.disabled,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            errorCursorColor = UiColors.textContent.error
        ),
        onValueChange = {
            if (it.length <= maxChar) {
                onValueChange.invoke(it)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        trailingIcon = {
            if (value.length < 4) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error),
                    tint = UiColors.textContent.error,
                    contentDescription = null
                )
            }
        }
    )
    if (value.length < 4) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.spacer_16),
                    bottom = dimensionResource(id = R.dimen.profile_input_field_text_padding)
                ),
            text = if (value.isEmpty()) stringResource(id = R.string.this_field_is_required)
            else stringResource(id = R.string.please_input_a_valid_year),
            color = UiColors.textContent.error,
            style = MaterialTheme.typography.subtitle2
        )
    }
}
