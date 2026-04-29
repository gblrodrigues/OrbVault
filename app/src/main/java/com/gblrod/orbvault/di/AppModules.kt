package com.gblrod.orbvault.di

import com.gblrod.orbvault.data.countries.remote.api.CountriesAPI
import com.gblrod.orbvault.data.weather.remote.api.WeatherAPI
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.weather.viewmodel.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Network (Countries)
    single(qualifier = named("countriesRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Network (Weather)
    single(qualifier = named("weatherRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API (Countries)
    single<CountriesAPI> {
        get<Retrofit>(qualifier = named("countriesRetrofit")).create(CountriesAPI::class.java)
    }

    // API (Weather)
    single<WeatherAPI> {
        get<Retrofit>(qualifier = named("weatherRetrofit")).create(WeatherAPI::class.java)
    }

    // ViewModel (Countries)
    viewModel {
        CountriesViewModel(repository = get())
    }

    viewModel {
        CountryDetailsViewModel(
            countriesRepository = get(),
            favoriteRepository = get()
        )
    }

    viewModel {
        ExploreViewModel(
            api = get()
        )
    }

    // ViewModel (Weather)
    viewModel {
        WeatherViewModel(
            repository = get()
        )
    }
}