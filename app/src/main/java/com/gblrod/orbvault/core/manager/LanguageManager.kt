package com.gblrod.orbvault.core.manager

import android.content.Context
import android.content.res.Configuration
import androidx.core.content.edit
import com.gblrod.orbvault.core.utils.getDeviceLanguage
import com.gblrod.orbvault.ui.language.LanguageOptions
import java.util.Locale

object LanguageManager {
    fun getStoredLanguage(context: Context): LanguageOptions? {
        val prefs = context.getSharedPreferences("language_cache", Context.MODE_PRIVATE)
        val value = prefs.getString("lang", null)
        return value?.let { LanguageOptions.valueOf(it) }
    }

    fun persistLanguage(context: Context, language: LanguageOptions) {
        context.getSharedPreferences("language_cache", Context.MODE_PRIVATE)
            .edit {
                putString("lang", language.name)
            }
    }

    fun resolveLocale(language: LanguageOptions?): Locale {
        return when (language ?: getDeviceLanguage()) {
            LanguageOptions.PT_BR -> Locale.forLanguageTag("pt-BR")
            LanguageOptions.EN_US -> Locale.forLanguageTag("en")
            LanguageOptions.ES -> Locale.forLanguageTag("es")
        }
    }

    fun applyLocale(base: Context, locale: Locale): Context {
        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        return base.createConfigurationContext(config)
    }
}