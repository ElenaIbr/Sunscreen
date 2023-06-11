package com.example.sunscreen.ui.questionnaire.models

import com.example.sunscreen.R

enum class QuestionStep(
    val number: Int,
    val question: Int
) {
    PersonalData(
        number = 0,
        question = R.string.personal_data_question
    ),
    SkinType (
        number = 1,
        question = R.string.skin_type_question
    ),
    SkinColor (
        number = 2,
        question = R.string.skin_color_question
    ),
    Notifications (
        number = 3,
        question = R.string.notifications_question
    );

    companion object {
        fun getNextStep(value: QuestionStep): QuestionStep {
            return if (value.number < QuestionStep.values().size) {
                fromStepByNumber(value.number + 1)
            } else value
        }

        fun getPreviousStep(value: QuestionStep): QuestionStep {
            return if (value.number != 0) { fromStepByNumber(value.number - 1)
            } else value
        }

        private fun fromStepByNumber(value: Int): QuestionStep = when (value) {
            0 -> PersonalData
            1 -> SkinType
            2 -> SkinColor
            3 -> Notifications
            else -> PersonalData
        }
    }
}
