package com.quiz.ui.quizquestion

import com.quiz.base.UIState
import com.quiz.domain.entities.Question

data class QuizQuestionUIState(
    val isLoading: Boolean = false,
    val questions: List<Question> = dummyQuestions.shuffled(),
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean? = null,
    val score: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val error: String? = null
): UIState


val dummyQuestions = listOf(
    Question(
        id = 1L,
        question = "What hidden feature do recent Android versions reveal when you tap the version number multiple times in Settings?",
        options = listOf(
            "Flappy Bird–style game",
            "Virtual pet",
            "Hidden performance menu",
            "System UI tuner"
        ),
        correctOptionIndex = 2
    ),
    Question(
        id = 1L,
        question = "What hidden feature do recent Android versions reveal when you tap the version number multiple times in Settings?",
        options = listOf(
            "Flappy Bird–style game",
            "Virtual pet",
            "Hidden performance menu",
            "System UI tuner"
        ),
        correctOptionIndex = 2
    ),
    Question(
        id = 1L,
        question = "What hidden feature do recent Android versions reveal when you tap the version number multiple times in Settings?",
        options = listOf(
            "Flappy Bird–style game",
            "Virtual pet",
            "Hidden performance menu",
            "System UI tuner"
        ),
        correctOptionIndex = 2
    )
)
