package com.gblrod.orbvault.data.preferences.model

import com.gblrod.orbvault.ui.language.LanguageOptions
import com.gblrod.orbvault.ui.theme.ThemeOptions

data class UserPreferences(
    val theme: ThemeOptions = ThemeOptions.SYSTEM,
    val language: LanguageOptions? = null
)