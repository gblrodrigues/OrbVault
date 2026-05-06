package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.theme.ButtonNext
import com.gblrod.orbvault.ui.theme.ButtonRestart
import com.gblrod.orbvault.ui.theme.QuizCardCorrectQuestion
import com.gblrod.orbvault.ui.theme.QuizIntermediateQuestions

@Composable
fun QuizResultCard(
    score: Int,
    total: Int,
    bestScore: Int,
    onRestart: () -> Unit,
    onExit: () -> Unit
) {
    val percentage = if (total > 0) {
        ((score.toFloat() / total) * 100).toInt()
    } else 0

    val bestPercentage = if (total > 0) {
        ((bestScore.toFloat() / total) * 100).toInt()
    } else 0

    val color = when {
        percentage >= 70 -> QuizCardCorrectQuestion
        percentage >= 40 -> QuizIntermediateQuestions
        else -> MaterialTheme.colorScheme.error
    }

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

            Card(
                modifier = Modifier
                    .size(
                        width = 220.dp,
                        height = 150.dp
                    ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.quiz_title_result_progress),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(id = R.string.quiz_result_progress, percentage),
                        style = MaterialTheme.typography.displayLarge,
                        color = color
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(
                            id = R.string.quiz_best_progress,
                            bestPercentage,
                            bestScore,
                            total
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            QuizActionButton(
                text = stringResource(id = R.string.quiz_button_play_again),
                onClick = onRestart,
                color = ButtonRestart,
                icon = Icons.Default.Refresh
            )

            Spacer(modifier = Modifier.height(4.dp))

            QuizActionButton(
                text = stringResource(id = R.string.quiz_button_return),
                onClick = onExit,
                color = ButtonNext,
                icon = Icons.Default.Explore
            )
        }
    }
}