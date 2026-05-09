package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.model.Question

@Composable
fun QuizQuestionCard(
    question: Question,
    answered: Boolean,
    isCorrect: Boolean,
    selectedOption: Int?,
    onSelectedOption: (Int) -> Unit
) {
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
                    desc = stringResource(
                        id = question.desc,
                        *question.args.toTypedArray()
                    )
                )
            }
        }
    }
}