package com.example.sunscreen.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.sunscreen.ui.theme.UiColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            }
            .focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(
                text = title,
                color = if (isFocused.value) UiColors.mainBrand.primary
                else UiColors.textContent.disabled,
                style = MaterialTheme.typography.subtitle1
            )
        },
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = UiColors.textContent.primary,
            focusedBorderColor = UiColors.mainBrand.primary,
            unfocusedBorderColor = UiColors.textContent.disabled,
            containerColor = UiColors.background.baseWhite,
            focusedLabelColor = UiColors.mainBrand.primary,
            unfocusedLabelColor = UiColors.textContent.disabled
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone.invoke()
            }
        )
    )
}
