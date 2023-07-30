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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R

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
                bottom = 8.dp
            ),
        text = title,
        fontWeight = FontWeight.Bold,
        color = if (value.length < 4) colorResource(id = R.color.error_color) else Color.Black,
        style = MaterialTheme.typography.subtitle1
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.text_field_color)
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        value = value,
        isError = value.length < 4,
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedLabelColor = colorResource(id = R.color.text_field_color),
            unfocusedLabelColor = colorResource(id = R.color.text_field_color),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            errorCursorColor = colorResource(id = R.color.error_color)
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
                    painter = painterResource(id = R.drawable.ic_info),
                    tint = colorResource(id = R.color.error_color),
                    contentDescription = null
                )
            }
        }
    )
}
