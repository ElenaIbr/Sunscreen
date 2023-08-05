package com.example.sunscreen.ui.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
                bottom = 8.dp
            ),
        text = title,
        fontWeight = FontWeight.Bold,
        color = UiColors.textContent.primary,
        style = MaterialTheme.typography.subtitle1
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = UiColors.textContent.disabled
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        value = value,
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = UiColors.textContent.disabled,
            backgroundColor = UiColors.background.baseWhite,
            focusedLabelColor = UiColors.textContent.disabled,
            unfocusedLabelColor = UiColors.textContent.disabled,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = { newValue ->
            onValueChange.invoke(newValue)
        }
    )
}
