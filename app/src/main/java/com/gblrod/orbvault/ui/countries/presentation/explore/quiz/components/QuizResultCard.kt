package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel.QuizViewModel
import com.gblrod.orbvault.ui.shared.components.ActionButton
import com.gblrod.orbvault.ui.theme.ButtonRestart
import kotlinx.coroutines.launch

@Composable
fun QuizResultCard(
    score: Int,
    total: Int,
    bestScore: Int,
    onRestart: () -> Unit,
    onExit: () -> Unit,
    quizViewModel: QuizViewModel,
    snackbarHostState: SnackbarHostState
) {
    val snackbarAction = stringResource(id = R.string.snackbar_action_label)
    val snackbarQuizMessage = stringResource(id = R.string.snackbar_quiz_message_reset)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.quiz_result_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stringResource(id = R.string.quiz_result_sub_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            QuizScoreCard(
                score = score,
                total = total,
                bestScore = bestScore,
                onResetBestScore = {
                    quizViewModel.resetBestScore(currentScore = bestScore)

                    scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = snackbarQuizMessage,
                                actionLabel = snackbarAction,
                                duration = SnackbarDuration.Short,
                                withDismissAction = true
                            )

                        if (result == SnackbarResult.ActionPerformed) {
                            quizViewModel.undoResetBestScore()
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ActionButton(
                    text = stringResource(id = R.string.quiz_button_play_again),
                    onClick = onRestart,
                    color = ButtonRestart,
                    icon = Icons.Default.Refresh
                )

                Spacer(modifier = Modifier.height(4.dp))

                ActionButton(
                    text = stringResource(id = R.string.action_explore_countries),
                    onClick = onExit,
                    color = Color.Transparent,
                    icon = Icons.Default.Explore,
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}