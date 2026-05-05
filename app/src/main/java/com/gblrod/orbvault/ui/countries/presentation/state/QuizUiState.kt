package com.gblrod.orbvault.ui.countries.presentation.state

import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.model.Question

sealed class QuizUiState {
    object Idle : QuizUiState()

    object Loading : QuizUiState()

    data class Success(
        val questions: List<Question>,
        val currentQuestion: Int = 0,
        val selectedOption: Int? = null,
        val answered: Boolean = false,
        val score: Int = 0
    ) : QuizUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : QuizUiState()

    data class Result(
        val score: Int,
        val total: Int
    ) : QuizUiState()
}