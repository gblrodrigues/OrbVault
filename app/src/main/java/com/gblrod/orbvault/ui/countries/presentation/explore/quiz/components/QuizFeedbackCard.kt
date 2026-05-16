package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.HourglassDisabled
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.theme.QuizCorrectBackground
import com.gblrod.orbvault.ui.theme.QuizCorrectText
import com.gblrod.orbvault.ui.theme.FeedbackCritical
import com.gblrod.orbvault.ui.theme.FeedbackWarning
import com.gblrod.orbvault.ui.theme.YellowDarkActions
import com.gblrod.orbvault.ui.theme.YellowActions

@Composable
fun QuizFeedbackCard(
    isCorrect: Boolean,
    timeExpired: Boolean,
    desc: String
) {
    val background = when{
        isCorrect -> QuizCorrectBackground
        timeExpired -> YellowDarkActions
        else -> FeedbackCritical
    }

    val textColor = when {
        isCorrect -> QuizCorrectText
        timeExpired -> YellowActions
        else -> FeedbackWarning
    }

    val textResult = when {
        isCorrect -> stringResource(id = R.string.quiz_question_correct_label)
        timeExpired -> stringResource(id = R.string.quiz_timer_expired)
        else -> stringResource(id = R.string.quiz_question_incorrect_label)
    }

    val icons = when{
        isCorrect -> Icons.Default.CheckCircleOutline
        timeExpired -> Icons.Default.HourglassDisabled
        else -> Icons.Default.RemoveCircleOutline
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icons,
                    contentDescription = null,
                    tint = textColor
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = textResult,
                    color = textColor
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = desc,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}