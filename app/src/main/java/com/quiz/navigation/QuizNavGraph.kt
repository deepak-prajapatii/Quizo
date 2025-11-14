package com.quiz.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.quiz.Screen
import com.quiz.ui.quizlanding.QuizLandingScreen
import com.quiz.ui.quizquestion.QuizQuestionScreen
import com.quiz.ui.result.QuizResultScreen


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
                QuizQuestionScreen { correctScore, totalQuestionCount, bestStreak ->
                    navController.navigate(
                        Screen.ShowResult.createRoute(
                            correct = correctScore,
                            total = totalQuestionCount,
                            bestStreak = bestStreak
                        )
                    )
                }
            }

            composable(
                route = Screen.ShowResult.route,
                arguments = listOf(
                    navArgument("correct") { type = NavType.IntType },
                    navArgument("total") { type = NavType.IntType },
                    navArgument("bestStreak") { type = NavType.IntType }
                )) { entry ->

                QuizResultScreen(onRestartQuiz = {
                    navController.navigate(Screen.QuizQuestion.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                })
            }
        }
    }
}