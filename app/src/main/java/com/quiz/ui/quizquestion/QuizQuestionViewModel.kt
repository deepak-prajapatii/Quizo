package com.quiz.ui.quizquestion

import androidx.lifecycle.viewModelScope
import com.quiz.base.BaseViewModel
import com.quiz.domain.usecase.GetAllQuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizQuestionViewModel @Inject constructor(
    private val getAllQuestionUseCase: GetAllQuestionUseCase
) : BaseViewModel<QuizQuestionUIState, QuizQuestionUIEvent>(initialState = QuizQuestionUIState()) {

    init {

    }

    private fun getAllQuestions() {
        viewModelScope.launch {

            updateState { state -> state.copy(isLoading = true) }
            getAllQuestionUseCase.execute(Unit)
                .collect { either ->
                    either.onSuccess { questions ->
                        updateState {
                            it.copy(
                                isLoading = false,
                                questions = questions,
                                currentQuestionIndex = 0,
                                selectedOptionIndex = null,
                                isAnswered = false,
                                isCorrect = null,
                                score = 0,
                                currentStreak = 0
                            )
                        }
                    }.onFailure {
                        //handle later
                    }
                }
        }
    }

    fun restartQuiz() {
        getAllQuestions()
    }

    fun onChoiceSelected(index: Int) {
        if (currentState.isAnswered) return

        val current = currentState.questions.getOrNull(currentState.currentQuestionIndex) ?: return
        val isCorrect = index == current.correctOptionIndex

        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        val newCurrentStreak = if (isCorrect) currentState.currentStreak + 1 else 0
        val newBest = maxOf(currentState.bestStreak, newCurrentStreak)

        updateState {
            it.copy(
                selectedOptionIndex = index,
                isAnswered = true,
                isCorrect = isCorrect,
                score = newScore,
                currentStreak = newCurrentStreak,
                bestStreak = newBest
            )
        }

        if (newBest > currentState.bestStreak) {
            viewModelScope.launch {
//                streakRepo.saveBestStreak(newBest)
            }
        }

        // auto-advance after delay
        viewModelScope.launch {
            delay(AUTO_DELAY_TIMER_IN_SEC)
            proceedToNext()
        }
    }

    private fun proceedToNext() {
        val nextIndex = currentState.currentQuestionIndex + 1
        if (nextIndex >= currentState.questions.size) {
            sendEvent(QuizQuestionUIEvent.ShowResult)
            return
        }
        updateState {
            it.copy(
                currentQuestionIndex = nextIndex,
                selectedOptionIndex = null,
                isAnswered = false,
                isCorrect = null
            )
        }
    }

    fun onSkip() {
        updateState { state -> state.copy(currentStreak = 0) }
        proceedToNext()
    }

    companion object {
        private const val AUTO_DELAY_TIMER_IN_SEC = 900L
    }
}