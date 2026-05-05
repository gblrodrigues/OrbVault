package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.screen.QuizScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel.QuizViewModel

fun NavGraphBuilder.quizRoute(
    navHostController: NavHostController,
    quizViewModel: QuizViewModel
) {
    composable(route = Routes.Quiz.route) {
        QuizScreen(
            navHostController = navHostController,
            quizViewModel = quizViewModel
        )
    }
}