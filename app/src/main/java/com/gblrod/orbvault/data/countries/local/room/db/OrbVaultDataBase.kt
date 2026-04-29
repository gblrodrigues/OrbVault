package com.gblrod.orbvault.data.countries.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gblrod.orbvault.data.countries.local.room.dao.FavoriteCountryDao
import com.gblrod.orbvault.data.countries.local.room.entity.FavoriteCountryEntity

@Database(
    entities = [FavoriteCountryEntity::class],
    version = 3,
    exportSchema = false
)
abstract class OrbVaultDataBase : RoomDatabase() {
    abstract fun favoriteCountryDao(): FavoriteCountryDao
}