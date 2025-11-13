package com.quiz.ui.quizquestion

import com.quiz.base.UIEvent

sealed class QuizQuestionUIEvent : UIEvent {
    object ShowResult: QuizQuestionUIEvent()
}