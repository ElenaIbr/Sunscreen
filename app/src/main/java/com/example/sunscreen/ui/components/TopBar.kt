package com.example.sunscreen.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.sunscreen.R
import com.example.sunscreen.ui.theme.UiColors

@Composable
fun TopBar(
    enabled: Boolean = true,
    onClick:() -> Unit
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(
                enabled = enabled,
                onClick = { onClick.invoke() }
            ) {
                Text(
                    text = stringResource(id = R.string.save).uppercase(),
                    style = MaterialTheme.typography.subtitle1,
                    color = if (enabled) UiColors.mainBrand.primary else UiColors.textContent.disabled                )
            }
        }
        Divider(
            color = UiColors.textContent.disabled
        )
    }
}
