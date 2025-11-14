package com.quiz.ui.quizquestion

import com.quiz.base.UIState
import com.quiz.domain.entities.Question

data class QuizQuestionUIState(
    val isLoading: Boolean = false,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean? = null,
    val score: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val error: String? = null
): UIState