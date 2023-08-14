package com.example.sunscreen.ui.questionnaire.viewmodel

import com.example.domain.models.UserModel
import com.example.sunscreen.ui.questionnaire.models.QuestionStep
import com.example.domain.models.Notification

interface QuestionnaireEvent

class SetUserName(val name: String): QuestionnaireEvent

class SetBirthDate(val date: String): QuestionnaireEvent

class SetSkinType(val skinType: UserModel.SkinType): QuestionnaireEvent

class SetSkinColor(val skinColor: UserModel.SkinColor): QuestionnaireEvent

class SetQuestionNumber(val questionStep: QuestionStep): QuestionnaireEvent

class EnableNotification(val notification: Notification): QuestionnaireEvent

class UpdateUser: QuestionnaireEvent
