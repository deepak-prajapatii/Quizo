package com.quiz.ui.result

import androidx.lifecycle.SavedStateHandle
import com.quiz.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuizResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<QuizResultUIState, QuizResultUIEvent>(initialState = QuizResultUIState()) {

    init {
        val correct: Int = savedStateHandle["correct"] ?: 0
        val total: Int = savedStateHandle["total"] ?: 0
        val bestSteak: Int = savedStateHandle["bestStreak"] ?: 0

        updateState { state ->
            state.copy(
                correct = correct, totalQuestions = total, bestStreak = bestSteak
            )
        }
    }
}