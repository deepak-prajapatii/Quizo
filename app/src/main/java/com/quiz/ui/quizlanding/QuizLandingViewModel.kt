package com.quiz.ui.quizlanding

import com.quiz.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizLandingViewModel @Inject constructor() :
    BaseViewModel<QuizLandingUIState, QuizLandingUIEvent>(initialState = QuizLandingUIState()) {
}