package com.gblrod.orbvault.ui.language.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.data.preferences.repository.UserPreferencesRepository
import com.gblrod.orbvault.ui.language.LanguageOptions
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {
    val language = repository.userPreferences
        .map { it.language }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun setLanguage(language: LanguageOptions) {
        viewModelScope.launch {
            repository.saveLanguage(language)
        }
    }
}