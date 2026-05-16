package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassDisabled
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.model.Question
import com.gblrod.orbvault.ui.theme.FeedbackCritical
import com.gblrod.orbvault.ui.theme.FeedbackWarning
import com.gblrod.orbvault.ui.theme.QuizProgressIndicator
import com.gblrod.orbvault.ui.theme.YellowActions

@Composable
fun QuizQuestionCard(
    question: Question,
    answered: Boolean,
    isCorrect: Boolean,
    isExpired: Boolean,
    selectedOption: Int?,
    onSelectedOption: (Int) -> Unit,
    remainingTime: Int,
    totalTime: Int
) {
    val timeExpired = remainingTime <= 0
    val progress = if (totalTime > 0) {
        ((remainingTime.toFloat() / totalTime) * 100).toInt()
    } else 0

    val colorTimer = when {
        progress <= 15 -> FeedbackCritical
        progress <= 30 -> FeedbackWarning
        progress <= 60 -> YellowActions
        else -> QuizProgressIndicator
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (timeExpired) Icons.Default.HourglassEmpty else Icons.Default.HourglassDisabled,
                    contentDescription = null,
                    tint = colorTimer
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (timeExpired) stringResource(
                        id = R.string.quiz_timer_title) else stringResource(
                        id = R.string.quiz_timer_expired),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = stringResource(id = R.string.quiz_timer, remainingTime),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorTimer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                    isSelected = selectedOption == index,
                    isCorrect = index == question.optionCorrect,
                    isAnswered = answered,
                    onClick = { onSelectedOption(index) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (answered) {
                QuizFeedbackCard(
                    isCorrect = isCorrect,
                    timeExpired = isExpired,
                    desc = stringResource(
                        id = question.desc,
                        *question.args.toTypedArray()
                    )
                )
            }
        }
    }
}