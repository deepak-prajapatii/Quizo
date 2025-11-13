package com.quiz.ui.quizscreen

import com.quiz.base.UIState
import com.quiz.domain.entities.Question

data class QuizUIState(
    val isLoading: Boolean = false,
    val questions: List<Question> = emptyList()
) : UIState