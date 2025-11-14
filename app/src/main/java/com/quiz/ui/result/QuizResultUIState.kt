package com.quiz.ui.result

import com.quiz.base.UIState

data class QuizResultUIState(
    val isLoading: Boolean = false,
    val correct: Int = 0,
    val totalQuestions: Int = 10,
    val bestStreak: Int = 0
): UIState