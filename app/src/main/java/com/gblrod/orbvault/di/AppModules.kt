package com.gblrod.orbvault.di

import com.gblrod.orbvault.data.network.CountriesAPI
import com.gblrod.orbvault.data.repository.CountriesRepository
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
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

    // Repository
    single {
        CountriesRepository(
            api = get()
        )
    }

    // ViewModel
    viewModelOf(constructor = ::CountriesViewModel)
    viewModelOf(constructor = ::CountryDetailsViewModel)

    viewModel {
        ExploreViewModel(
            api = get()
        )
    }
}