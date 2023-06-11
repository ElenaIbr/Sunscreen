package com.example.sunscreen.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R
import com.example.sunscreen.utils.DateTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputYearField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val maxChar = 8

    OutlinedTextField(
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
                color = colorResource(id = R.color.text_field_color),
                style = MaterialTheme.typography.subtitle1
            )
        },
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor =  colorResource(id = R.color.text_field_color),
            focusedBorderColor =  colorResource(id = R.color.text_field_color),
            unfocusedBorderColor = colorResource(id = R.color.text_field_color),
            containerColor = Color.White,
            focusedLabelColor =  colorResource(id = R.color.text_field_color),
            unfocusedLabelColor =  colorResource(id = R.color.text_field_color)
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        placeholder = {
            Text(
                text = "dd/mm/yyyy",
                color =  colorResource(id = R.color.text_field_color),
                style = MaterialTheme.typography.subtitle1
            )
        },
        visualTransformation = DateTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )
}
