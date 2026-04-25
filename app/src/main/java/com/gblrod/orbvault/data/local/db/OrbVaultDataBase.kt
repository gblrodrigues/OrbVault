package com.gblrod.orbvault.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gblrod.orbvault.data.local.dao.FavoriteCountryDao
import com.gblrod.orbvault.data.local.entity.FavoriteCountryEntity

@Database(
    entities = [FavoriteCountryEntity::class],
    version = 2,
    exportSchema = false
)
abstract class OrbVaultDataBase : RoomDatabase() {
    abstract fun favoriteCountryDao(): FavoriteCountryDao
}