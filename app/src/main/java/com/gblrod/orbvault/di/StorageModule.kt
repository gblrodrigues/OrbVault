package com.gblrod.orbvault.di

import androidx.room.Room
import com.gblrod.orbvault.data.countries.local.room.db.OrbVaultDataBase
import com.gblrod.orbvault.data.countries.local.room.migrations.MIGRATION_3_4
import com.gblrod.orbvault.data.countries.repository.CountriesRepository
import com.gblrod.orbvault.data.countries.repository.FavoriteRepository
import com.gblrod.orbvault.data.countries.repository.RecentCountryRepository
import com.gblrod.orbvault.data.weather.repository.WeatherRepository
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.data.QuizRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

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
            .addMigrations(MIGRATION_3_4)
            .build()
    }

    // Dao (Favorites)
    single {
        get<OrbVaultDataBase>().favoriteCountryDao()
    }

    // Dao (Recent Countries)
    single {
        get<OrbVaultDataBase>().recentCountryDao()
    }

    // Repository (Favorites)
    single {
        FavoriteRepository(
            favoriteCountryDao = get()
        )
    }

    // Repository (Recent Countries)
    single {
        RecentCountryRepository(
            recentCountryDao = get()
        )
    }

    // Repository (Quiz)
    single {
        QuizRepository(
            api = get()
        )
    }
}