package com.quiz.ui.quizscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiz.base.BaseViewModel
import com.quiz.domain.usecase.GetAllQuestionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizViewModel @Inject constructor(
    private val getAllQuestionUseCase: GetAllQuestionUseCase
) : BaseViewModel<QuizUIState, QuizUIEvent>(initialState = QuizUIState()) {

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            updateState { state -> state.copy(isLoading = true) }
            getAllQuestionUseCase.execute(Unit).collect { either ->
                either.onSuccess {
                    updateState { state -> state.copy(isLoading = false, questions = it) }
                }.onFailure {
                    // handle exceptions later
                }
            }
        }
    }
}