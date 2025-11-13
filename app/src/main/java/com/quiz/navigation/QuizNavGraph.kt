package com.quiz.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quiz.Screen
import com.quiz.ui.quizlanding.QuizLandingScreen
import com.quiz.ui.quizquestion.QuizQuestionScreen


const val NAV_HOST_ROUTE = "main-route"

@Composable
fun QuizNavGraph() {
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screen.QuizLanding.route,
            route = NAV_HOST_ROUTE
        ) {
            composable(Screen.QuizLanding.route) {
                QuizLandingScreen {
                    navController.navigate(Screen.QuizQuestion.route)
                }
            }
            composable(Screen.QuizQuestion.route) {
                QuizQuestionScreen() {
                    navController.navigate(Screen.ShowResult.route)
                }
            }
        }
    }
}