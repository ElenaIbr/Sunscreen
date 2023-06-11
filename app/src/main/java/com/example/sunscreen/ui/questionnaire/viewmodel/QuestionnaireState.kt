package com.example.sunscreen.ui.questionnaire.viewmodel

import com.example.domain.models.UserModel
import com.example.sunscreen.ui.notifications.models.Notification
import com.example.sunscreen.ui.questionnaire.models.QuestionStep

data class QuestionnaireState (
    val user: UserModel? = null,
    val userName: String = "",
    val birthDate: String = "",
    val skinType: UserModel.SkinType? = null,
    val skinColor: UserModel.SkinColor? = null,
    val questionStep: QuestionStep = QuestionStep.PersonalData,
    val notification: Notification? = null,
    val showSplashScreen: Boolean = true
)
