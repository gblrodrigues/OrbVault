package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.ui.theme.OptionBackground

@Composable
fun QuizOptionCard(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswered: Boolean,
    onClick: () -> Unit
) {
    val backgroundResult = when {
        !isAnswered -> Color.Transparent
        isCorrect -> Color.Green
        isSelected -> MaterialTheme.colorScheme.error
        else -> Color.Transparent
    }
    Card(
        modifier = Modifier
            .width(400.dp)
            .padding(vertical = 4.dp)
            .clickable(
                enabled = !isAnswered,
                onClick = onClick
            )
            .border(
                width = 2.dp,
                color = backgroundResult,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = OptionBackground),
    )
    {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}