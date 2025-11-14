package com.quiz

sealed class Screen(val route: String, val name: String) {
    object QuizLanding: Screen(route = "quiz_landing", name = "Quiz Landing")
    object QuizQuestion: Screen(route = "quiz_question", name = "Quiz Question")
    object ShowResult: Screen(route = "show_result/{correct}/{total}/{bestStreak}", name = "Show Result") {
        fun createRoute(correct: Int, total: Int, bestStreak: Int): String =
            "show_result/$correct/$total/$bestStreak"
    }
}