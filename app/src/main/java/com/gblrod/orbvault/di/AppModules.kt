package com.gblrod.orbvault.di

import com.gblrod.orbvault.network.CountriesAPI
import com.gblrod.orbvault.ui.presentation.viewmodel.CountriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    // Network
    single {
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API
    single<CountriesAPI> {
        get<Retrofit>().create(CountriesAPI::class.java)
    }

    // ViewModel
    viewModel {
        CountriesViewModel(
            api = get()
        )
    }
}