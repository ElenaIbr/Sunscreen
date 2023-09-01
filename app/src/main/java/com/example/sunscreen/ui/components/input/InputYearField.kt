package com.example.sunscreen.ui.components.input

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputYearField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val maxChar = 4
    val isFocused = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            },
        value = value,
        onValueChange = {
            if (it.length <= maxChar) {
                onValueChange.invoke(it)
            }
        },
        singleLine = true,
        label = {
            Text(
                text = title,
                color = if (isFocused.value) UiColors.mainBrand.primary
                else UiColors.textContent.disabled,
                style = MaterialTheme.typography.subtitle1
            )
        },
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.input_field_corner),
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = UiColors.textContent.primary,
            focusedBorderColor = UiColors.mainBrand.primary,
            unfocusedBorderColor = UiColors.textContent.disabled,
            containerColor = UiColors.background.baseWhite,
            focusedLabelColor = UiColors.mainBrand.primary,
            unfocusedLabelColor = UiColors.textContent.disabled
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        placeholder = {
            Text(
                text = "YYYY",
                color =  UiColors.textContent.disabled,
                style = MaterialTheme.typography.subtitle1
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )
}
