package com.example.sunscreen.ui.questionnaire

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.UserModel
import com.example.sunscreen.R
import com.example.sunscreen.ui.components.SplashScreen
import com.example.sunscreen.ui.components.buttons.ButtonState
import com.example.sunscreen.ui.components.buttons.PrimaryButton
import com.example.sunscreen.ui.components.buttons.SecondaryButton
import com.example.sunscreen.ui.components.input.InputField
import com.example.sunscreen.ui.components.input.InputYearField
import com.example.sunscreen.ui.questionnaire.components.QuestionList
import com.example.sunscreen.ui.questionnaire.models.QuestionStep
import com.example.sunscreen.ui.questionnaire.viewmodel.QuestionnaireViewModel
import com.example.sunscreen.ui.questionnaire.viewmodel.SetBirthDate
import com.example.sunscreen.ui.questionnaire.viewmodel.SetQuestionNumber
import com.example.sunscreen.ui.questionnaire.viewmodel.SetSkinColor
import com.example.sunscreen.ui.questionnaire.viewmodel.SetSkinType
import com.example.sunscreen.ui.questionnaire.viewmodel.SetUserName
import com.example.sunscreen.ui.questionnaire.viewmodel.UpdateUser
import com.example.sunscreen.ui.theme.UiColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuestionnaireScreen(
    onCompletedClick: () -> Unit
) {
    val viewModel: QuestionnaireViewModel = hiltViewModel()
    val questionsState by viewModel.questionsState.collectAsState()

    val systemUiController = rememberSystemUiController()
    val activity = LocalContext.current as Activity

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    BackHandler {
        when(questionsState.questionStep) {
            QuestionStep.PersonalData -> {
                activity.finish()
            }
            QuestionStep.SkinType -> {
                viewModel.sendEvent(SetQuestionNumber(QuestionStep.PersonalData))
            }
            QuestionStep.SkinColor -> {
                viewModel.sendEvent(SetQuestionNumber(QuestionStep.SkinType))
            }
        }
    }

    LaunchedEffect(key1 = questionsState.user) {
        if (questionsState.user != null) onCompletedClick.invoke()
    }

    if (questionsState.showSplashScreen) {
        SplashScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.questionnaire_screen_top_padding)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = dimensionResource(id = R.dimen.questionnaire_title_padding)
                        ),
                    color = UiColors.textContent.primary,
                    textAlign = TextAlign.Center,
                    style = androidx.compose.material.MaterialTheme.typography.h6,
                    text = stringResource(id = questionsState.questionStep.question)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.questionnaire_indicator_horizontal_padding),
                        end = dimensionResource(id = R.dimen.questionnaire_indicator_horizontal_padding),
                        bottom = dimensionResource(id = R.dimen.questionnaire_indicator_bottom_padding),
                    )
            ) {
                repeat(QuestionStep.values().size) { iteration ->
                    val color = if (
                        iteration == questionsState.questionStep.number ||
                        iteration < questionsState.questionStep.number
                    ) UiColors.mainBrand.primary
                    else UiColors.mainBrand.primary.copy(alpha = 0.3F)

                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.questionnaire_indicator_height))
                            .weight(3F)
                            .padding(horizontal = dimensionResource(id = R.dimen.questionnaire_indicator_internal_padding))
                            .background(color)
                            .fillMaxHeight()
                    )
                }
            }
            Column(
                Modifier
                    .weight(0.55F)
                    .padding(horizontal = dimensionResource(id = R.dimen.questionnaire_content_padding))
            ) {
                when (questionsState.questionStep) {
                    QuestionStep.PersonalData -> {
                        InputField(
                            value = questionsState.userName,
                            onValueChange = { value ->
                                viewModel.sendEvent(SetUserName(value))
                            },
                            onDone = {
                                if (questionsState.userName.isNotEmpty()) {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            },
                            title = stringResource(id = R.string.name)
                        )
                        Spacer(
                            modifier = Modifier.height(
                                dimensionResource(id = R.dimen.spacer_24)
                            )
                        )
                        InputYearField(
                            value = questionsState.birthDate,
                            onValueChange = { value ->
                                viewModel.sendEvent(SetBirthDate(value))
                                if (value.length == 4) {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            },
                            title = stringResource(id = R.string.dateOfBirth)
                        )
                    }
                    QuestionStep.SkinType -> {
                        QuestionList(
                            variants = UserModel.SkinType.values().map { it.name },
                            selectedItem = questionsState.skinType?.name ?: "",
                            onItemClick = { type ->
                                viewModel.sendEvent(
                                    SetSkinType(UserModel.SkinType.fromValue(type))
                                )
                            }
                        )
                    }
                    QuestionStep.SkinColor -> {
                        QuestionList(
                            variants = UserModel.SkinColor.values().map { it.name },
                            selectedItem = questionsState.skinColor?.name ?: "",
                            onItemClick = { type ->
                                viewModel.sendEvent(
                                    SetSkinColor(UserModel.SkinColor.fromValue(type))
                                )
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.questionnaire_buttons_padding)
                    )
                    .weight(0.15F),
                horizontalArrangement = if (questionsState.questionStep.number == 1) Arrangement.End else Arrangement.SpaceBetween
            ) {
                if (questionsState.questionStep.number != 0) {
                    SecondaryButton(
                        modifier = Modifier.weight(0.5F),
                        text = stringResource(id = R.string.previous),
                        buttonState = ButtonState.DEFAULT,
                        onClick = {
                            viewModel.sendEvent(
                                SetQuestionNumber(QuestionStep.getPreviousStep(questionsState.questionStep))
                            )
                        }
                    )
                    Spacer(
                        modifier = Modifier.width(
                            dimensionResource(id = R.dimen.spacer_16)
                        )
                    )
                }
                PrimaryButton(
                    modifier = Modifier.weight(0.5F),
                    text = if (questionsState.questionStep.number == QuestionStep.values().last().number) stringResource(id = R.string.complete)
                    else stringResource(id = R.string.next),
                    buttonState = when (questionsState.questionStep) {
                        QuestionStep.PersonalData -> {
                            if (questionsState.userName.isNotEmpty() && questionsState.birthDate.length == 4) ButtonState.DEFAULT
                            else ButtonState.DISABLED
                        }
                        QuestionStep.SkinType -> {
                            if (questionsState.skinType == null) ButtonState.DISABLED else ButtonState.DEFAULT
                        }
                        QuestionStep.SkinColor -> ButtonState.DEFAULT
                    },
                    onClick = {
                        if (
                            questionsState.questionStep.number != QuestionStep.values().last().number
                        ) {
                            viewModel.sendEvent(
                                SetQuestionNumber(QuestionStep.getNextStep(questionsState.questionStep))
                            )
                        } else {
                            viewModel.sendEvent(UpdateUser())
                            onCompletedClick.invoke()
                        }
                    }
                )
            }
        }
    }
}
