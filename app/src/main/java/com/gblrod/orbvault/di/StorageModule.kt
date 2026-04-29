package com.gblrod.orbvault.di

import androidx.room.Room
import com.gblrod.orbvault.data.countries.local.room.db.OrbVaultDataBase
import com.gblrod.orbvault.data.countries.local.room.migrations.MIGRATION_2_3
import com.gblrod.orbvault.data.countries.repository.CountriesRepository
import com.gblrod.orbvault.data.countries.repository.FavoriteRepository
import com.gblrod.orbvault.data.weather.repository.WeatherRepository
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