package com.quiz.ui.result

import com.quiz.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuizResultViewModel @Inject constructor() :
    BaseViewModel<QuizResultUIState, QuizResultUIEvent>(initialState = QuizResultUIState()) {

}