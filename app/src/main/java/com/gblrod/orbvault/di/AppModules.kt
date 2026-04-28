package com.gblrod.orbvault.di

import androidx.room.Room
import com.gblrod.orbvault.data.local.db.OrbVaultDataBase
import com.gblrod.orbvault.data.local.db.migrations.MIGRATION_2_3
import com.gblrod.orbvault.data.network.countries.CountriesAPI
import com.gblrod.orbvault.data.network.weather.WeatherApi
import com.gblrod.orbvault.data.repository.countries.CountriesRepository
import com.gblrod.orbvault.data.repository.favorites.FavoriteRepository
import com.gblrod.orbvault.data.repository.weather.WeatherRepository
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.weather.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidContext
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
    single<WeatherApi> {
        get<Retrofit>(qualifier = named("weatherRetrofit")).create(WeatherApi::class.java)
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

val storageModule = module {

    // Repository Countries -> (API)
    single {
        CountriesRepository(
            api = get()
        )
    }

    // Repository Weather -> (API)
    single {
        WeatherRepository(
            api = get()
        )
    }

    // Room
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = OrbVaultDataBase::class.java,
            name = "OrbVault.db"
        )
            .addMigrations(MIGRATION_2_3)
            .build()
    }

    // Dao
    single {
        get<OrbVaultDataBase>().favoriteCountryDao()
    }

    // Repository (Favorites)
    single {
        FavoriteRepository(
            favoriteCountryDao = get()
        )
    }
}