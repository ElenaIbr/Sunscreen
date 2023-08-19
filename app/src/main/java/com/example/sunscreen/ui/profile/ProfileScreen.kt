package com.example.sunscreen.ui.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.models.UserModel
import com.example.sunscreen.R
import com.example.sunscreen.navigation.BottomBar
import com.example.sunscreen.ui.components.DropDown
import com.example.sunscreen.ui.components.TopBar
import com.example.sunscreen.ui.profile.components.ProfileTextDateField
import com.example.sunscreen.ui.profile.components.ProfileTextField
import com.example.sunscreen.ui.profile.viewmodel.ProfileViewModel
import com.example.sunscreen.ui.profile.viewmodel.UpdateUser
import com.example.sunscreen.ui.profile.viewmodel.UpdateUserBirthDate
import com.example.sunscreen.ui.profile.viewmodel.UpdateUserName
import com.example.sunscreen.ui.profile.viewmodel.UpdateUserSkinColor
import com.example.sunscreen.ui.profile.viewmodel.UpdateUserSkinType
import com.example.sunscreen.ui.questionnaire.components.QuestionItem
import com.example.sunscreen.ui.theme.UiColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val profileState by viewModel.profileState.collectAsState()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val systemUiController = rememberSystemUiController()
    val statusBarColor = UiColors.background.baseWhite

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = true
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBar(
                enabled = profileState.userDataWasChanged && profileState.updatedUserName?.isNotEmpty() == true
                        && profileState.updatedBirthDate?.length == 4,
                onClick = {
                    viewModel.sendEvent(UpdateUser())
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
                .padding(
                    bottom = paddings.calculateBottomPadding(),
                    top = paddings.calculateTopPadding(),
                    start = dimensionResource(id = R.dimen.profile_padding),
                    end = dimensionResource(id = R.dimen.profile_padding)
                )
                .verticalScroll(rememberScrollState())
        ) {
            profileState.updatedUserName?.let { userName ->
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_20))
                )
                ProfileTextField(
                    title = stringResource(id = R.string.name),
                    value = userName,
                    onValueChange = { newValue ->
                        viewModel.sendEvent(UpdateUserName(newValue))
                    }
                )
            }
            profileState.updatedBirthDate ?.let { birthDate ->
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_20))
                )
                ProfileTextDateField(
                    title = stringResource(id = R.string.dateOfBirth),
                    value = birthDate,
                    onValueChange = { newValue ->
                        viewModel.sendEvent(UpdateUserBirthDate(newValue))
                    }
                )
            }
            profileState.updatedSkinType?.let { skinType ->
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_24))
                )
                DropDown(
                    label = stringResource(id = R.string.Skin_type),
                    value = skinType.name
                ) {
                    UserModel.SkinType.values().map { it.name }.forEach { type ->
                        QuestionItem(
                            value = type,
                            isSelected = UserModel.SkinType.fromValue(type) == skinType,
                            onItemClick = {
                                viewModel.sendEvent(
                                    UpdateUserSkinType(UserModel.SkinType.fromValue(type))
                                )
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_12))
                        )
                    }
                }
            }
            profileState.updatedSkinColor?.let { skinColor ->
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_20))
                )
                DropDown(
                    label = stringResource(id = R.string.Skin_color),
                    value = skinColor.name
                ) {
                    UserModel.SkinColor.values().map { it.name }.forEach { color ->
                        QuestionItem(
                            value = color,
                            isSelected = UserModel.SkinColor.fromValue(color) == skinColor,
                            onItemClick = {
                                viewModel.sendEvent(
                                    UpdateUserSkinColor(UserModel.SkinColor.fromValue(color))
                                )
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(
                                dimensionResource(id = R.dimen.spacer_12)
                            )
                        )
                    }
                }
            }
        }
    }
}
