package com.example.sunscreen.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.sunscreen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
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
            cursorColor = colorResource(id = R.color.text_field_color),
            focusedBorderColor = colorResource(id = R.color.text_field_color),
            unfocusedBorderColor = colorResource(id = R.color.text_field_color),
            containerColor = Color.White,
            focusedLabelColor = colorResource(id = R.color.text_field_color),
            unfocusedLabelColor = colorResource(id = R.color.text_field_color)
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone.invoke()
            }
        )
    )
}
