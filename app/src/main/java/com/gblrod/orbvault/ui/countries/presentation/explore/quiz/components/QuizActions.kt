package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.shared.components.ActionButton
import com.gblrod.orbvault.ui.theme.ButtonNext
import com.gblrod.orbvault.ui.theme.ButtonRestart
import com.gblrod.orbvault.ui.theme.ButtonResult

@Composable
fun QuizActions(
    currentQuestion: Int,
    questionSize: Int,
    quizStarted: Boolean,
    answered: Boolean,
    onNextQuestion: () -> Unit,
    onFinish: () -> Unit,
    onRestart: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (quizStarted) {
            ActionButton(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.quiz_restart_question),
                onClick = { onRestart() },
                color = ButtonRestart,
                icon = Icons.Default.Restore
            )
        }

        if (answered) {
            if (currentQuestion < questionSize) {
                ActionButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.quiz_next_question),
                    onClick = { onNextQuestion() },
                    color = ButtonNext,
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    iconAtEnd = true
                )
            } else {
                ActionButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.quiz_show_result),
                    onClick = { onFinish() },
                    color = ButtonResult,
                    icon = Icons.Default.Poll
                )
            }
        }
    }
}