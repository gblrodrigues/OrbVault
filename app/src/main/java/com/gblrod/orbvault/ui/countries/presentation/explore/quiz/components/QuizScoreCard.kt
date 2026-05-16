package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.theme.FeedbackWarning
import com.gblrod.orbvault.ui.theme.QuizCardCorrectQuestion
import com.gblrod.orbvault.ui.theme.QuizIntermediateQuestions

@Composable
fun QuizScoreCard(
    score: Int,
    total: Int,
    bestScore: Int,
    onResetBestScore: () -> Unit
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

    var showQuizResetDialog by remember { mutableStateOf(false) }

    val canResetScore = bestScore > 0

    val resetIconTint = if (canResetScore) {
        FeedbackWarning
    } else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

    Card(
        modifier = Modifier
            .size(
                width = 250.dp,
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

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
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

                IconButton(
                    onClick = { showQuizResetDialog = true },
                    enabled = canResetScore
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = null,
                        tint = resetIconTint
                    )
                }
            }

            if (showQuizResetDialog) {
                QuizResetScore(
                    bestScore = bestPercentage,
                    onResetBestScore = { onResetBestScore() },
                    onDismiss = { showQuizResetDialog = false }
                )
            }
        }
    }
}