package com.gblrod.orbvault.di

import com.gblrod.orbvault.data.preferences.datastore.dataStore
import com.gblrod.orbvault.data.preferences.repository.UserPreferencesRepository
import com.gblrod.orbvault.ui.language.viewmodel.LanguageViewModel
import com.gblrod.orbvault.ui.theme.viewmodel.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val preferencesModule = module {

    // DataStore
    single { androidContext().dataStore }

    singleOf(constructor = ::UserPreferencesRepository)

    // viewModel
    viewModelOf(constructor = ::ThemeViewModel)
    viewModelOf(constructor = ::LanguageViewModel)
}