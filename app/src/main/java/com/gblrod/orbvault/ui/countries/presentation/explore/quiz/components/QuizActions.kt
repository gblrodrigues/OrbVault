package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        if (answered) {
            if (currentQuestion < questionSize) {
                ActionButton(
                    text = stringResource(id = R.string.quiz_next_question),
                    onClick = { onNextQuestion() },
                    color = ButtonNext,
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    iconAtEnd = true
                )
            } else {
                ActionButton(
                    text = stringResource(id = R.string.quiz_show_result),
                    onClick = { onFinish() },
                    color = ButtonResult,
                    icon = Icons.Default.Poll
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (quizStarted) {
            ActionButton(
                text = stringResource(id = R.string.quiz_restart_question),
                onClick = { onRestart() },
                color = ButtonRestart,
                icon = Icons.Default.Restore
            )
        }
    }
}