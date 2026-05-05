package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun QuizHeader(
    currentQuestion: Int,
    total: Int
) {
    Text(
        text = "${currentQuestion + 1} / $total",
        fontSize = 17.sp,
        color = MaterialTheme.colorScheme.onSurface
    )
}