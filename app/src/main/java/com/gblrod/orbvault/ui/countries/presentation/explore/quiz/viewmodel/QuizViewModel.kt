package com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.preferences.repository.UserPreferencesRepository
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.config.QuizDefaults.QUIZ_TIMER_DURATION
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.config.QuizDefaults.QUIZ_TIMER_INTERVAL
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.QuizRepository
import com.gblrod.orbvault.ui.countries.presentation.state.QuizUiState
import com.gblrod.orbvault.ui.shared.utils.safeApiCall
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _quizUiState = MutableStateFlow<QuizUiState>(QuizUiState.Idle)
    val quizUiState: StateFlow<QuizUiState> = _quizUiState

    private var timerJob: Job? = null
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

            safeApiCall(
                onHttpError = { code ->
                    _quizUiState.value =
                        QuizUiState.Error(
                            messageResId = R.string.ui_state_http_exception,
                            code = code
                        )
                },
                onIoError = {
                    _quizUiState.value =
                        QuizUiState.Error(messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _quizUiState.value =
                        QuizUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val questions = repository.getQuestions()

                if (questions.isEmpty()) {
                    _quizUiState.value =
                        QuizUiState.Error(messageResId = R.string.quiz_ui_state_generic_error)
                } else {
                    _quizUiState.value = QuizUiState.Success(
                        questions = questions,
                        quizStarted = false
                    )
                }
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
                answered = false,
                timeExpired = false,
                remainingTime = QUIZ_TIMER_DURATION,
            )
            startQuestionTimer()
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

        stopTimer()
        _quizUiState.value = currentState.copy(
            selectedOption = index,
            answered = true,
            timeExpired = false,
            score = if (isCorrect) currentState.score + 1 else currentState.score
        )
    }

    fun startQuiz() {
        val currentState = _quizUiState.value

        if (currentState !is QuizUiState.Success) return
        if (currentState.quizStarted) return

        _quizUiState.value = currentState.copy(
            quizStarted = true
        )

        startQuestionTimer()
    }

    fun pauseTimer() {
        stopTimer()
    }

    fun resumeTimer() {
        val currentState = _quizUiState.value

        when {
            currentState !is QuizUiState.Success -> return
            currentState.answered -> return
            timerJob?.isActive == true -> return
        }
        startQuestionTimer()
    }

    fun onTimeExpired() {
        val currentState = _quizUiState.value

        if (currentState !is QuizUiState.Success) return

        stopTimer()

        _quizUiState.value = currentState.copy(
            answered = true,
            timeExpired = true
        )
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun startQuestionTimer() {
        stopTimer()

        timerJob = viewModelScope.launch {
            while (true) {
                delay(QUIZ_TIMER_INTERVAL)

                val currentState = _quizUiState.value

                when {
                    currentState !is QuizUiState.Success -> break
                    currentState.answered -> break
                    currentState.remainingTime > 0 -> {
                        _quizUiState.value = currentState.copy(
                            remainingTime = currentState.remainingTime - 1
                        )
                    }

                    else -> {
                        onTimeExpired()
                        break
                    }
                }
            }
        }
    }

    fun restart() {
        stopTimer()
        _quizUiState.value = QuizUiState.Loading
        loadQuestions()
    }

    fun finish() {
        val currentState = _quizUiState.value

        if (currentState !is QuizUiState.Success) return

        viewModelScope.launch {
            userPreferencesRepository.saveBestScore(score = currentState.score)
        }

        stopTimer()
        _quizUiState.value = QuizUiState.Result(
            score = currentState.score,
            total = currentState.questions.size
        )
    }

    fun retry() {
        stopTimer()
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