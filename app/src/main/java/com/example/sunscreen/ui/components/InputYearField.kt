package com.example.sunscreen.ui.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R

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
                color = if (isFocused.value) colorResource(id = R.color.secondary_color_1)
                else colorResource(id = R.color.text_field_color),
                style = MaterialTheme.typography.subtitle1
            )
        },
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.secondary_color_1),
            unfocusedBorderColor = colorResource(id = R.color.text_field_color),
            containerColor = Color.White,
            focusedLabelColor = colorResource(id = R.color.secondary_color_1),
            unfocusedLabelColor = colorResource(id = R.color.text_field_color)
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        placeholder = {
            Text(
                text = "YYYY",
                color =  colorResource(id = R.color.text_field_color),
                style = MaterialTheme.typography.subtitle1
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )
}
