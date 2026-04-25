package com.gblrod.orbvault.di

import androidx.room.Room
import com.gblrod.orbvault.data.local.db.OrbVaultDataBase
import com.gblrod.orbvault.data.local.db.migrations.MIGRATION_1_2
import com.gblrod.orbvault.data.network.CountriesAPI
import com.gblrod.orbvault.data.repository.CountriesRepository
import com.gblrod.orbvault.data.repository.FavoriteRepository
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

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
}

val storageModule = module {

    // Repository (API)
    single {
        CountriesRepository(
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
            .addMigrations(MIGRATION_1_2)
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