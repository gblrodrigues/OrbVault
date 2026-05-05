package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.QuizRepository
import com.gblrod.orbvault.ui.countries.presentation.state.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

class QuizViewModel(
    private val repository: QuizRepository
) : ViewModel() {
    private val _quizUiState = MutableStateFlow<QuizUiState>(QuizUiState.Idle)
    val quizUiState: StateFlow<QuizUiState> = _quizUiState

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _quizUiState.value = QuizUiState.Loading

            try {
                val questions = repository.getQuestions()

                if (questions.isEmpty()) {
                    _quizUiState.value = QuizUiState.Error(messageResId = R.string.quiz_ui_state_generic_error)
                } else {
                    _quizUiState.value = QuizUiState.Success(questions = questions)
                }

            } catch (e: IOException) {
                _quizUiState.value = QuizUiState.Error(messageResId = R.string.quiz_ui_state_ioexception)

            } catch (e: Exception) {
                _quizUiState.value = QuizUiState.Error(messageResId = R.string.quiz_ui_state_httpexception)
            }
        }
    }

    fun nextQuestion() {
        val currentState = _quizUiState.value

        if (currentState !is QuizUiState.Success) return

        if (currentState.currentQuestion < currentState.questions.lastIndex) {
            _quizUiState.value = currentState.copy(
                currentQuestion = currentState.currentQuestion + 1,
                selectedOption = null,
                answered = false
            )
        } else {
            finish()
        }
    }

    fun selectOption(index: Int) {
        val currentState = _quizUiState.value

        if (currentState !is QuizUiState.Success) return
        if (currentState.answered) return

        val question = currentState.questions[currentState.currentQuestion]
        val isCorrect = index == question.optionCorrect

        _quizUiState.value = currentState.copy(
            selectedOption = index,
            answered = true,
            score = if (isCorrect) currentState.score + 1 else currentState.score
        )
    }

    fun restart() {
        loadQuestions()
    }

    fun finish() {
        val currentState = _quizUiState.value

        if (currentState !is QuizUiState.Success) return

        _quizUiState.value = QuizUiState.Result(
            score = currentState.score,
            total = currentState.questions.size
        )
    }
}