package com.gblrod.orbvault.navigation.graph

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.screen.QuizScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel.QuizViewModel

fun NavGraphBuilder.quizRoute(
    navHostController: NavHostController,
    quizViewModel: QuizViewModel,
    snackbarHostState: SnackbarHostState
) {
    composable(route = Routes.Quiz.route) {
        QuizScreen(
            navHostController = navHostController,
            quizViewModel = quizViewModel,
            snackbarHostState = snackbarHostState
        )
    }
}