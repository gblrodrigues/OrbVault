package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.preferences.repository.UserPreferencesRepository
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.QuizRepository
import com.gblrod.orbvault.ui.countries.presentation.state.QuizUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class QuizViewModel(
    private val repository: QuizRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _quizUiState = MutableStateFlow<QuizUiState>(QuizUiState.Idle)
    val quizUiState: StateFlow<QuizUiState> = _quizUiState

    private var lastBestScore: Int? = null
    val bestScore = userPreferencesRepository.userPreferences
        .map { it.bestScore }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0
        )

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _quizUiState.value = QuizUiState.Loading

            try {
                val questions = repository.getQuestions()

                if (questions.isEmpty()) {
                    _quizUiState.value =
                        QuizUiState.Error(messageResId = R.string.quiz_ui_state_generic_error)
                } else {
                    _quizUiState.value = QuizUiState.Success(questions = questions)
                }

            } catch (e: HttpException) {
                _quizUiState.value =
                    QuizUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = e.code()
                    )
            } catch (e: IOException) {
                _quizUiState.value =
                    QuizUiState.Error(messageResId = R.string.ui_state_io_exception)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _quizUiState.value =
                    QuizUiState.Error(messageResId = R.string.ui_state_generic_error)
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

        viewModelScope.launch {
            userPreferencesRepository.saveBestScore(score = currentState.score)
        }

        _quizUiState.value = QuizUiState.Result(
            score = currentState.score,
            total = currentState.questions.size
        )
    }

    fun retry() {
        loadQuestions()
    }

    fun resetBestScore(currentScore: Int? = null) {
        viewModelScope.launch {
            lastBestScore = currentScore
            userPreferencesRepository.resetScore()
        }
    }
    fun undoResetBestScore() {
        viewModelScope.launch {
            lastBestScore?.let {
                userPreferencesRepository.restoreBestScore(it)
                lastBestScore = null
            }
        }
    }
}