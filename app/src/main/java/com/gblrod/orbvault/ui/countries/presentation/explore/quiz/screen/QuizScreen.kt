package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizActions
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizQuestionCard
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizProgress
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizResultCard
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel.QuizViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.QuizUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen

@Composable
fun QuizScreen(
    navHostController: NavHostController,
    quizViewModel: QuizViewModel
) {
    val uiState by quizViewModel.quizUiState.collectAsState()
    val bestScore by quizViewModel.bestScore.collectAsState()

    when (val state = uiState) {
        is QuizUiState.Idle -> {}

        is QuizUiState.Loading -> {
            LoadingScreen()
        }

        is QuizUiState.Error -> {
            val message = if (state.code == null) {
                stringResource(id = state.messageResId)
            } else {
                stringResource(id = state.messageResId, state.code)
            }

            ErrorMessage(
                message = message,
                onRetry = { quizViewModel.restart() }
            )
        }

        is QuizUiState.Result -> {
            QuizResultCard(
                score = state.score,
                total = state.total,
                bestScore = bestScore,
                onRestart = {
                    quizViewModel.restart()
                },
                onExit = {
                    navHostController.popBackStack()
                }
            )
        }

        is QuizUiState.Success -> {
            val question = state.questions[state.currentQuestion]
            val questionSize = state.questions.size
            val currentQuestion = state.currentQuestion + 1
            val quizStarted = currentQuestion > 1 || state.answered

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuizProgress(
                    currentQuestion = currentQuestion,
                    questionSize = questionSize
                )

                QuizQuestionCard(
                    question = question,
                    isCorrect = state.selectedOption == question.optionCorrect,
                    answered = state.answered,
                    selectedOption = state.selectedOption,
                    onSelectedOption = { index ->
                        quizViewModel.selectOption(index)
                    }
                )

                QuizActions(
                    currentQuestion = currentQuestion,
                    questionSize = questionSize,
                    quizStarted = quizStarted,
                    answered = state.answered,
                    onNextQuestion = { quizViewModel.nextQuestion() },
                    onRestart = { quizViewModel.restart() },
                    onFinish = { quizViewModel.finish() }
                )
            }
        }
    }
}