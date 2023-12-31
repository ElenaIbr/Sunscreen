package com.example.sunscreen.ui.questionnaire.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Notification
import com.example.domain.models.UserModel
import com.example.domain.usecases.GetUserEntity
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.UpdateUserUseCase
import com.example.sunscreen.ui.questionnaire.models.QuestionStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _questionsState = MutableStateFlow(QuestionnaireState())
    val questionsState: StateFlow<QuestionnaireState> = _questionsState

    init {
        getUser()
    }

    fun sendEvent(questionnaireEvent: QuestionnaireEvent) {
        when(questionnaireEvent) {
            is SetUserName -> {
                setUserName(questionnaireEvent.name)
            }
            is SetBirthDate -> {
                setBirthDate(questionnaireEvent.date)
            }
            is SetSkinType -> {
                setSkinType(questionnaireEvent.skinType)
            }
            is SetSkinColor -> {
                setSkinColor(questionnaireEvent.skinColor)
            }
            is SetQuestionNumber -> {
                setQuestionNumber(questionnaireEvent.questionStep)
            }
            is EnableNotification -> {
                EnableNotification(questionnaireEvent.notification)
            }
            is UpdateUser -> {
                updateUser()
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.execute(Unit).collect { flow ->
                when (flow) {
                    is GetUserEntity.Loading -> {
                        _questionsState.value = _questionsState.value.copy(
                            showSplashScreen = true
                        )
                    }
                    is GetUserEntity.Success -> {
                        _questionsState.value = _questionsState.value.copy(
                            user = flow.userModel,
                            showSplashScreen = flow.userModel != null
                        )
                    }
                }
            }
        }
    }

    private fun setUserName(name: String) {
        _questionsState.value = _questionsState.value.copy(
            userName = name
        )
    }

    private fun setBirthDate(date: String) {
        _questionsState.value = _questionsState.value.copy(
            birthDate = date
        )
    }

    private fun setSkinType(skinType: UserModel.SkinType) {
        _questionsState.value = _questionsState.value.copy(
            skinType = skinType
        )
    }

    private fun setSkinColor(skinColor: UserModel.SkinColor) {
        _questionsState.value = _questionsState.value.copy(
            skinColor = skinColor
        )
    }

    private fun setQuestionNumber(questionStep: QuestionStep) {
        _questionsState.value = _questionsState.value.copy(
            questionStep = questionStep
        )
    }

    private fun enableNotification(notification: Notification) {
        _questionsState.value = _questionsState.value.copy(
            notification = notification
        )
    }

    private fun updateUser() {
        viewModelScope.launch {
            updateUserUseCase.execute(
                UserModel(
                    id = UUID.randomUUID(),
                    name = _questionsState.value.userName,
                    birthDate = _questionsState.value.birthDate,
                    skinType = _questionsState.value.skinType ?: UserModel.SkinType.Unknown,
                    skinColor = _questionsState.value.skinColor ?: UserModel.SkinColor.Unknown,
                    coordinates = null,
                    notifications = Notification()
                )
            )
        }
    }
}
