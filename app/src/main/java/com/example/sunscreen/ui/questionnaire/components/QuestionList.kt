package com.example.sunscreen.ui.questionnaire.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.sunscreen.R

@Composable
fun QuestionList(
    variants: List<String>,
    selectedItem: String = "",
    onItemClick: (String) -> Unit
) {
    val selected = remember { mutableStateOf(selectedItem) }

    Column {
        variants.forEach { question ->
            QuestionItem(
                value = question,
                isSelected = selected.value == question,
                onItemClick = {
                    selected.value = question
                    onItemClick.invoke(question)
                }
            )
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_12))
            )
        }
    }
}
