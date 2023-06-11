package com.example.sunscreen.ui.profile

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.models.UserModel
import com.example.sunscreen.R
import com.example.sunscreen.navigation.BottomBar
import com.example.sunscreen.ui.components.TopBar
import com.example.sunscreen.ui.profile.viewmodel.ProfileViewModel
import com.example.sunscreen.ui.questionnaire.components.QuestionItem
import com.example.sunscreen.utils.DateTransformation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(navController: NavHostController) {

    val viewModel: ProfileViewModel = hiltViewModel()
    val profileState by viewModel.profileState.collectAsState()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBar(
                enabled = profileState.userDataWasChanged,
                onClick = {
                    viewModel.updateUser()
                    keyboardController?.hide()
                    Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .verticalScroll(rememberScrollState())
        ) {
            profileState.updatedUserName?.let { userName ->
                ProfileTextField(
                    title = stringResource(id = R.string.name),
                    value = userName,
                    onValueChange = { newValue ->
                        viewModel.updateUserName(newValue)
                    }
                )
            }
            profileState.updatedBirthDate ?.let { birthDate ->
                ProfileTextDateField(
                    title = stringResource(id = R.string.dateOfBirth),
                    value = birthDate,
                    onValueChange = { newValue ->
                        viewModel.updateUserBirthDate(newValue)
                    }
                )
            }
            profileState.updatedSkinType?.let { skinType ->
                DropDown(
                    text = "Skin type: $skinType"
                ) {
                    UserModel.SkinType.values().map { it.name }.forEach { type ->
                        QuestionItem(
                            value = type,
                            isSelected = UserModel.SkinType.fromValue(type) == skinType,
                            onItemClick = {
                                viewModel.updateUserSkinType(
                                    UserModel.SkinType.fromValue(type)
                                )
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                    }
                }
            }
            profileState.updatedSkinColor?.let { skinColor ->
                DropDown(
                    text = "Skin color: $skinColor"
                ) {
                    UserModel.SkinColor.values().map { it.name }.forEach { color ->
                        QuestionItem(
                            value = color,
                            isSelected = UserModel.SkinColor.fromValue(color) == skinColor,
                            onItemClick = {
                                viewModel.updateUserSkinColor(
                                    UserModel.SkinColor.fromValue(color)
                                )
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                    }
                }
            }
        }
    }
}

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
                vertical = 16.dp,
                horizontal = 16.dp
            ),
        text = title,
        color = colorResource(id = R.color.primary_text_color),
        style = MaterialTheme.typography.subtitle2
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            )
            .clip(RoundedCornerShape(4.dp))
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.text_field_color)
                ),
                shape = RoundedCornerShape(4.dp)
            ),
        value = value,
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(4.dp),
        colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
            cursorColor = colorResource(id = R.color.text_field_color),
            backgroundColor = Color.White,
            focusedLabelColor = colorResource(id = R.color.text_field_color),
            unfocusedLabelColor = colorResource(id = R.color.text_field_color),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            onValueChange.invoke(it)
        }
    )
}

@Composable
fun ProfileTextDateField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val maxChar = 8

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
        text = title,
        color = colorResource(id = R.color.primary_text_color),
        style = MaterialTheme.typography.subtitle2
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            )
            .clip(RoundedCornerShape(4.dp))
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.text_field_color)
                ),
                shape = RoundedCornerShape(4.dp)
            ),
        value = value,
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(4.dp),
        colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
            cursorColor = colorResource(id = R.color.text_field_color),
            backgroundColor = Color.White,
            focusedLabelColor = colorResource(id = R.color.text_field_color),
            unfocusedLabelColor = colorResource(id = R.color.text_field_color),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            if (it.length <= maxChar) {
                onValueChange.invoke(it)
            }
        },
        visualTransformation = DateTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )
}

@Composable
fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var isOpen by remember { mutableStateOf(initiallyOpened) }

    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500
        )
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isOpen = !isOpen
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                color = colorResource(id = R.color.primary_text_color),
                style = MaterialTheme.typography.subtitle2
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = Color.Black,
                modifier = Modifier
                    .scale(1f, if (isOpen) -1f else 1f)
            )
        }
        if (isOpen) {
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0.5f, 0f)
                    }
                    .alpha(alpha.value)
            ) {
                content()
            }
        }
    }
}
