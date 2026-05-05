package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizActionButton
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizFeedbackCard
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizHeader
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizOptionCard
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components.QuizResultCard
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel.QuizViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.QuizUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen
import com.gblrod.orbvault.ui.theme.ButtonNext
import com.gblrod.orbvault.ui.theme.ButtonRestart
import com.gblrod.orbvault.ui.theme.ButtonResult
import com.gblrod.orbvault.ui.theme.QuizCardBackground

@Composable
fun QuizScreen(
    navHostController: NavHostController,
    quizViewModel: QuizViewModel
) {
    val uiState by quizViewModel.quizUiState.collectAsState()

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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuizHeader(
                    currentQuestion = state.currentQuestion,
                    total = state.questions.size
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .border(
                            width = 2.dp,
                            color = Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = QuizCardBackground)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = question.text),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        question.options.forEachIndexed { index, option ->
                            QuizOptionCard(
                                text = option,
                                isSelected = state.selectedOption == index,
                                isCorrect = index == question.optionCorrect,
                                isAnswered = state.answered,
                                onClick = { quizViewModel.selectOption(index) }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (state.answered) {
                            QuizFeedbackCard(
                                isCorrect = state.selectedOption == question.optionCorrect,
                                desc = stringResource(
                                    id = question.desc,
                                    *question.args.toTypedArray()
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            if (state.currentQuestion < state.questions.lastIndex) {
                                QuizActionButton(
                                    text = stringResource(id = R.string.quiz_next_question),
                                    onClick = { quizViewModel.nextQuestion() },
                                    color = ButtonNext
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                QuizActionButton(
                                    text = stringResource(id = R.string.quiz_restart_question),
                                    onClick = { quizViewModel.restart() },
                                    color = ButtonRestart
                                )
                            } else {
                                QuizActionButton(
                                    text = stringResource(id = R.string.quiz_show_result),
                                    onClick = {
                                        quizViewModel.finish()
                                    },
                                    color = ButtonResult.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}