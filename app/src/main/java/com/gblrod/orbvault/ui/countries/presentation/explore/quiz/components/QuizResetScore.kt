package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.theme.ButtonContainerDialog

@Composable
fun QuizResetScore(
    onResetBestScore: () -> Unit,
    onDismiss: () -> Unit,
    bestScore: Int
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(16.dp)
            ),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.quiz_alert_dialog_title)
            )
        },
        text = {
            Text(
                text = stringResource(
                    id = R.string.quiz_alert_dialog_description,
                    bestScore
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onResetBestScore()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonContainerDialog
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.quiz_alert_dialog_confirm),
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = ButtonContainerDialog
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.quiz_alert_dialog_dismiss),
                    color = ButtonContainerDialog
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}